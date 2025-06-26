package com.mohamed.myapplication.screens.details

import android.widget.Toast
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.mohamed.components.Constants
import com.mohamed.components.Constants.DEFAULT_PADDING
import com.mohamed.components.MyProgress
import com.mohamed.components.MyText
import com.mohamed.domain.entities.OrderItemEntity
import com.mohamed.domain.entities.OrderStatus


@Composable
fun DetailsScreen(
    orderId: Int, viewModel: DetailsViewModel = hiltViewModel()
) {
    val context = LocalContext.current

    val uiState by viewModel.uiState.collectAsState()
    val effect by viewModel.effect.collectAsStateWithLifecycle()



    LaunchedEffect(effect) {
        when (val currentEffect = effect) {

            is DetailsContract.DetailsEffect.ShowError -> {
                Toast.makeText(context, currentEffect.message, Toast.LENGTH_SHORT).show()
                viewModel.consumeEffect()
            }

            else -> {}
        }
    }

    LaunchedEffect(orderId) {
        viewModel.handleIntent(DetailsContract.OrderDetailsIntent.LoadOrderDetails(orderId))
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(DEFAULT_PADDING),
        verticalArrangement = Arrangement.spacedBy(DEFAULT_PADDING)
    ) {
        if (uiState.isLoading) {
            MyProgress()
        } else {

            uiState.orderDetails?.customerName?.let { CustomerCard(it) }

            uiState.orderStatusEntity?.let {
                OrderStatusProgress(
                    modifier = Modifier
                        .padding(10.dp)
                        .size(150.dp)
                        .align(Alignment.CenterHorizontally),
                    status = it.orderStatus, progress = it.progress,
                    preferredColor = it.preferredColor
                )
            }

            uiState.orderDetails?.items?.let { OrderItemsCard(items = it) }

            uiState.orderDetails?.totalAmount?.let {
                MyText(
                    "Total Price : $it",
                    modifier = Modifier
                        .padding(DEFAULT_PADDING)
                        .fillMaxWidth()
                        .background(Color.Blue, shape = RoundedCornerShape(12.dp))
                        .padding(DEFAULT_PADDING),
                    textColor = Color.White,
                    textSize = 25.sp
                )
            }

        }
    }
}

@Composable
fun CustomerCard(restaurantName: String) {
    Card(
        colors = CardColors(
            containerColor = Color.White,
            contentColor = Color.White,
            disabledContainerColor = Color.Gray,
            disabledContentColor = Color.Gray
        ),
        shape = RoundedCornerShape(Constants.DEFAULT_PADDING),
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Row(
            modifier = Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Default.Person,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier.size(24.dp)
            )
            Spacer(modifier = Modifier.width(Constants.DEFAULT_PADDING))
            MyText(
                text = restaurantName
            )
        }
    }
}


@Composable
fun OrderStatusProgress(
    modifier: Modifier = Modifier,
    status: OrderStatus,
    progress: Float,
    preferredColor: Color
) {
    val animatedProgress by animateFloatAsState(
        targetValue = progress.coerceIn(0f, 1f),
        animationSpec = tween(durationMillis = 500),
        label = "ProgressAnimation"
    )

    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator(
            modifier = modifier
                .fillMaxSize(),
            color = Color.Gray,
            trackColor = preferredColor,
            progress = { animatedProgress })
        MyText(
            text = status.value
        )
    }
}

@Composable
fun OrderItemsCard(modifier: Modifier = Modifier, items: List<OrderItemEntity>) {
    Card(
        colors = CardColors(
            containerColor = Color.White,
            contentColor = Color.White,
            disabledContainerColor = Color.Gray,
            disabledContentColor = Color.Gray
        ),
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(Constants.DEFAULT_PADDING),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Default.ShoppingCart,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.size(20.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                MyText(
                    text = "Order Items"
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            items.forEachIndexed { index, item ->
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        MyText(
                            text = item.name
                        )
                        MyText(
                            text = "Qty: ${item.quantity}"
                        )
                    }

                    MyText(
                        text = item.price
                    )
                }

                if (index < items.size - 1) {
                    Spacer(modifier = Modifier.height(Constants.DEFAULT_PADDING))
                    HorizontalDivider(color = Color(0xFFE0E0E0))
                    Spacer(modifier = Modifier.height(Constants.DEFAULT_PADDING))
                }
            }
        }
    }
}

