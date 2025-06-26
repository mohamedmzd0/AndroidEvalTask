package com.mohamed.domain.usecase

import com.mohamed.domain.entities.OrderDetailsEntity
import com.mohamed.domain.repo.OrdersRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetOrderDetailsUseCase @Inject constructor(private val repository: OrdersRepository) {
    suspend operator fun invoke(id: Int): Result<OrderDetailsEntity> {
        return repository.getOrderDetails(id)
    }
}