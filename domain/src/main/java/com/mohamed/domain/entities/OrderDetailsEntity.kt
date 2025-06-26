package com.mohamed.domain.entities

data class OrderDetailsEntity(
    val id: Int,
    val customerName: String,
    val totalAmount: String,
    val items: List<OrderItemEntity>
)

data class OrderItemEntity(
    val name: String,
    val quantity: Int,
    val price: String
)