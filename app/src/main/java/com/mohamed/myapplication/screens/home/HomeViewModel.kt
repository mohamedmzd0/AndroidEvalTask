package com.mohamed.myapplication.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mohamed.domain.entities.OrderEntity
import com.mohamed.domain.usecase.GetOrdersListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getOrdersListUseCase: GetOrdersListUseCase
) : ViewModel() {
    private val _state = MutableStateFlow(HomeContract.HomeState())
    val state: StateFlow<HomeContract.HomeState> = _state.asStateFlow()

    private val _effect = MutableStateFlow<HomeContract.HomeEffect?>(null)
    val effect: StateFlow<HomeContract.HomeEffect?> = _effect.asStateFlow()


    init {
        handleIntent(HomeContract.HomeIntent.LoadItems)
    }

    fun handleIntent(intent: HomeContract.HomeIntent) {
        when (intent) {
            is HomeContract.HomeIntent.LoadItems -> loadItems()
            is HomeContract.HomeIntent.NavigateToDetails -> navigateToDetails(intent.item)
        }
    }


    private fun loadItems() {
        viewModelScope.launch {
            _state.value = _state.value.copy(isLoading = true)

            try {
                delay(1000)

                val result = getOrdersListUseCase.invoke(false)

                if (result.isSuccess) {
                    _state.value = _state.value.copy(
                        items = result.getOrNull() ?: emptyList(), isLoading = false
                    )
                } else {
                    _state.value = _state.value.copy(
                        isLoading = false
                    )
                    _effect.value = HomeContract.HomeEffect.ShowError("Failed ")
                }


            } catch (e: Exception) {
                _state.value = _state.value.copy(
                    isLoading = false
                )
                _effect.value = HomeContract.HomeEffect.ShowError("Failed ")

            }
        }
    }


    private fun navigateToDetails(item: OrderEntity) {
        _effect.value = HomeContract.HomeEffect.NavigateToDetails(item)
    }

    fun consumeEffect() {
        _effect.value = null
    }

}