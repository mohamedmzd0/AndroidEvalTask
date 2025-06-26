package com.mohamed.data.repo

import com.google.gson.Gson
import com.mohamed.data.local.OrderDao
import com.mohamed.data.mappers.OrderDetailsMapper
import com.mohamed.data.mappers.OrderListMapper
import com.mohamed.data.mappers.OrderStatusMapper
import com.mohamed.data.remote.ApiService
import com.mohamed.domain.entities.OrderDetailsEntity
import com.mohamed.domain.entities.OrderEntity
import com.mohamed.domain.entities.OrderStatus
import com.mohamed.domain.entities.OrderStatusEntity
import com.mohamed.domain.repo.OrdersRepository
import io.socket.client.Socket
import io.socket.emitter.Emitter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flowOn
import org.json.JSONObject
import javax.inject.Inject

class OrdersRepositoryImpl @Inject constructor(
    private val apiService: ApiService,
    private val ordersListMapper: OrderListMapper,
    private val orderDetailsMapper: OrderDetailsMapper,
    private val orderStatusMapper: OrderStatusMapper,
    private val socket: Socket,
    private val dao: OrderDao
) : OrdersRepository {

    override suspend fun getOrders(forceRefresh: Boolean): Result<List<OrderEntity>> {
        return try {
            val data = if (forceRefresh || dao.getOrders().isEmpty()) {
                val remoteData = apiService.getItems()
                dao.clearOrders()
                dao.insertOrders(remoteData)
                remoteData
            } else {
                dao.getOrders()
            }
            Result.success(ordersListMapper.map(data) ?: emptyList())
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getOrderDetails(orderId: Int): Result<OrderDetailsEntity> {
        return try {
            Result.success(orderDetailsMapper.map(apiService.getOrderDetails(orderId)))
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getOrderStatus(orderId: Int): Flow<OrderStatusEntity> {
        return callbackFlow {

            /*********
             *  orderid, status
             * *****/

            val statuses = listOf(
                OrderStatus.Pending,
                OrderStatus.Preparing,
                OrderStatus.OnWay,
                OrderStatus.Delivered
            )

            for (status in statuses) {
                trySend(orderStatusMapper.map(status.value))
                delay(2000L)
            }


            if (true) return@callbackFlow

            if (!socket.connected()) {
                socket.connect()
            }


            socket.emit("socketOrderId", orderId)
            val statusListener = Emitter.Listener { args ->
                try {
                    if (args.isNotEmpty()) {
                        val jsonObject = JSONObject(args.toString())
                        val socketOrderId = jsonObject.optInt("orderId")
                        val orderStatus = jsonObject.optString("status")
                        if (orderId == socketOrderId)
                            trySend(orderStatusMapper.map(orderStatus))
                    }
                } catch (e: Exception) {
                    close(e)
                }
            }

            val connectErrorListener = Emitter.Listener { args ->
                val error = Exception("Socket connection error: ${args.getOrNull(0)}")
                close(error)
            }
            val disconnectListener = Emitter.Listener {
                close(Exception("Socket disconnected"))
            }
            socket.on("order_status_update", statusListener)
            socket.on(Socket.EVENT_CONNECT_ERROR, connectErrorListener)
            socket.on(Socket.EVENT_DISCONNECT, disconnectListener)

            awaitClose {
                socket.off("order_status_update", statusListener)
                socket.off(Socket.EVENT_CONNECT_ERROR, connectErrorListener)
                socket.off(Socket.EVENT_DISCONNECT, disconnectListener)
                socket.disconnect()
            }
        }.flowOn(Dispatchers.IO)
    }
}