package com.mohamed.data.mappers

import com.mohamed.data.model.OrderDetailsResponse
import com.mohamed.domain.entities.OrderDetailsEntity
import com.mohamed.domain.entities.OrderItemEntity
import javax.inject.Inject

class OrderDetailsMapper @Inject constructor() {
    fun map(orderDetails: OrderDetailsResponse): OrderDetailsEntity {
        return OrderDetailsEntity(
            id = orderDetails.id,
            customerName = orderDetails.customerName,
            totalAmount = "EGP %.2f".format(
                orderDetails.items
                    .sumOf { (it.price.toDouble()) * (it.quantity.toDouble()) }
            ),
            items = orderDetails.items.map {
                OrderItemEntity(
                    name = it.name,
                    quantity = it.quantity,
                    price = "${it.price},EGP"
                )
            }
        )
    }

}