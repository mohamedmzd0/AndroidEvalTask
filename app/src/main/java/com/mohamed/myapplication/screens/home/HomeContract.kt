package com.mohamed.myapplication.screens.home

import com.mohamed.domain.entities.OrderEntity

class HomeContract {

    data class HomeState(
        val items: List<OrderEntity> = emptyList(),
        val isLoading: Boolean = false
        )


    sealed class HomeIntent {
        data object LoadItems : HomeIntent()
        data class NavigateToDetails(val item: OrderEntity) : HomeIntent()
    }

    sealed class HomeEffect {
        data class NavigateToDetails(val item: OrderEntity) : HomeEffect()
        data class ShowError(val message: String) : HomeEffect()
    }

}