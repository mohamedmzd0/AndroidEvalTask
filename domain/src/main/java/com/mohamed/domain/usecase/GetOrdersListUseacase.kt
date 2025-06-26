package com.mohamed.domain.usecase

import com.mohamed.domain.entities.OrderEntity
import com.mohamed.domain.repo.OrdersRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetOrdersListUseCase @Inject constructor(private val ordersRepository: OrdersRepository) {

    suspend operator fun invoke(forceRefresh: Boolean): Result<List<OrderEntity>> {
        return ordersRepository.getOrders(forceRefresh)
    }
}