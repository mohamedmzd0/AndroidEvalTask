package com.mohamed.myapplication.screens.details

import com.mohamed.domain.entities.OrderDetailsEntity
import com.mohamed.domain.entities.OrderStatusEntity

class DetailsContract {

    data class OrderDetailsUiState(
        val isLoading: Boolean = false,
        val orderDetails: OrderDetailsEntity? = null,
        val orderStatusEntity: OrderStatusEntity? = null,
    )

    sealed class OrderDetailsIntent {
        data class LoadOrderDetails(val orderId: Int) : OrderDetailsIntent()
        data class StartOrderStatusListener(val orderId: Int) : OrderDetailsIntent()
    }

    sealed class DetailsEffect {
        data class ShowError(val message: String) : DetailsEffect()
    }
}