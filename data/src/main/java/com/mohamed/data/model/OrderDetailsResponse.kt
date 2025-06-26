package com.mohamed.data.model

data class OrderDetailsResponse(
    val id:Int,
    val customerName: String,
    val orderStatus: String,
    val progress: Float,
    val items: List<OrderItem>
)

data class OrderItem(
    val name: String,
    val quantity: Int,
    val price: Int
)