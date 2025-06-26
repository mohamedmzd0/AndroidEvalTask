package com.mohamed.data.mappers

import com.mohamed.domain.entities.OrderStatus
import com.mohamed.domain.entities.OrderStatusEntity
import javax.inject.Inject

class OrderStatusMapper @Inject constructor() {
    fun map(status: String): OrderStatusEntity {
        val orderStatus = OrderStatus.getStatus(status)
        return OrderStatusEntity(
            preferredColor = OrderStatus.getColor(orderStatus),
            orderStatus = orderStatus,
            progress = OrderStatus.getProgress(orderStatus),
        )
    }

}