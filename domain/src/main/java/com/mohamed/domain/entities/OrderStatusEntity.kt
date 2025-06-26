package com.mohamed.domain.entities

import androidx.compose.ui.graphics.Color

data class OrderStatusEntity(
    val orderStatus: OrderStatus,
    val progress: Float,
    val preferredColor: Color,
)

sealed class OrderStatus(val value: String) {

    data object Pending : OrderStatus("pending")
    data object Preparing : OrderStatus("Preparing")
    data object OnWay : OrderStatus("OnWay")
    data object Delivered : OrderStatus("Delivered")

    companion object {
        fun getStatus(categoryType: String?): OrderStatus {
            return when (categoryType) {
                Pending.value -> Pending
                Preparing.value -> Preparing
                OnWay.value -> OnWay
                else -> Delivered
            }
        }


        fun getProgress(status: OrderStatus): Float {
            return when (status) {
                is Pending -> 0.0f
                is Preparing -> 0.33f
                is OnWay -> 0.66f
                is Delivered -> 1.0f
            }
        }

        fun getColor(status: OrderStatus): Color {
            return when (status) {
                is Pending -> Color(0xFFFF9800)
                is Preparing -> Color(0xFF2196F3)
                is OnWay -> Color(0xFF9C27B0)
                is Delivered -> Color(0xFF4CAF50)
            }
        }
    }
}