package com.mohamed.domain.entities

import androidx.compose.ui.graphics.Color

data class OrderEntity(
    val id: Int,
    val customerName: String,
    val restaurantName: String,
    val orderStatus: String,
)
