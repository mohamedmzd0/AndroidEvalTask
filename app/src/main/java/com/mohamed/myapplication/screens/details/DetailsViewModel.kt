package com.mohamed.myapplication.screens.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mohamed.domain.usecase.GetOrderDetailsUseCase
import com.mohamed.domain.usecase.GetOrderStatusUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(
    private val getOrderDetailsUseCase: GetOrderDetailsUseCase,
    private val getOrderStatusUseCase: GetOrderStatusUseCase,
) : ViewModel() {

    private val _uiState = MutableStateFlow(DetailsContract.OrderDetailsUiState())
    val uiState: StateFlow<DetailsContract.OrderDetailsUiState> = _uiState.asStateFlow()


    private val _effect = MutableStateFlow<DetailsContract.DetailsEffect?>(null)
    val effect: StateFlow<DetailsContract.DetailsEffect?> = _effect.asStateFlow()


    fun handleIntent(intent: DetailsContract.OrderDetailsIntent) {
        when (intent) {
            is DetailsContract.OrderDetailsIntent.LoadOrderDetails -> loadOrderDetails(intent.orderId)
            is DetailsContract.OrderDetailsIntent.StartOrderStatusListener -> startOrderStatusUpdates(intent.orderId)
        }
    }


    private fun loadOrderDetails(orderId: Int) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(
                isLoading = true
            )


            try {
                val result = getOrderDetailsUseCase.invoke(orderId)

                if (result.isSuccess) {
                    _uiState.value = _uiState.value.copy(
                        orderDetails = result.getOrNull(), isLoading = false
                    )

                    handleIntent(DetailsContract.OrderDetailsIntent.StartOrderStatusListener(orderId))
                } else {
                    _uiState.value = _uiState.value.copy(isLoading = false)
                    _effect.value = DetailsContract.DetailsEffect.ShowError("Failed Load Api")
                }


            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(isLoading = false)
                _effect.value = DetailsContract.DetailsEffect.ShowError("Failed Load Api")

            }

        }
    }


    private var statusJob: Job? = null

    private fun startOrderStatusUpdates(orderId: Int) {
        statusJob = viewModelScope.launch {
            getOrderStatusUseCase(orderId).collect { result ->
                result.fold(onSuccess = { orderStatus ->
                    _uiState.value = _uiState.value.copy(orderStatusEntity = orderStatus)
                }, onFailure = { exception ->
                    _effect.value = DetailsContract.DetailsEffect.ShowError("Failed Load Socket")
                })
            }
        }
    }


    override fun onCleared() {
        super.onCleared()
        statusJob?.cancel()
    }

    fun consumeEffect() {
        _effect.value = null
    }
}


