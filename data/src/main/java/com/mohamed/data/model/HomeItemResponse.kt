package com.mohamed.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "orders")
data class HomeItemResponse(
    @PrimaryKey val id: Int,
    val clientName: String,
    val restaurantName: String,
    val orderStatus: String,
)
