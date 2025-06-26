package com.mohamed.domain.usecase

import com.mohamed.domain.entities.OrderStatusEntity
import com.mohamed.domain.repo.OrdersRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetOrderStatusUseCase @Inject constructor(private val repository: OrdersRepository) {
    suspend operator fun invoke(orderId: Int): Flow<Result<OrderStatusEntity>> {
        return repository.getOrderStatus(orderId)
            .map { orderStatus ->
                Result.success(orderStatus)
            }
            .catch { exception ->
                emit(Result.failure(exception))
            }
    }
}