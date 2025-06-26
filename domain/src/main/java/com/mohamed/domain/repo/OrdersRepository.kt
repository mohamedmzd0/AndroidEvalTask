package com.mohamed.domain.repo

import com.mohamed.domain.entities.OrderDetailsEntity
import com.mohamed.domain.entities.OrderEntity
import com.mohamed.domain.entities.OrderStatusEntity
import kotlinx.coroutines.flow.Flow

interface OrdersRepository {
    suspend fun getOrders(forceRefresh: Boolean): Result<List<OrderEntity>>
    suspend fun getOrderDetails(orderId: Int): Result<OrderDetailsEntity>

    suspend fun getOrderStatus(orderId: Int): Flow<OrderStatusEntity>
}