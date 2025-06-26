package com.mohamed.data.mappers

import com.mohamed.data.model.HomeItemResponse
import com.mohamed.domain.entities.OrderEntity
import javax.inject.Inject

class OrderListMapper @Inject constructor() {
    fun map(list: List<HomeItemResponse>?): List<OrderEntity>? {
        return list?.map {
            OrderEntity(
                id = it.id,
                customerName = it.clientName,
                restaurantName = it.restaurantName,
                orderStatus = it.orderStatus
            )
        }
    }
}