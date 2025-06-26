package com.mohamed.myapplication.screens.home

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.mohamed.components.Constants
import com.mohamed.components.MyProgress
import com.mohamed.components.MyText
import com.mohamed.domain.entities.OrderEntity

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun HomeScreen(
    onNavigateToDetails: (Int) -> Unit,
    viewModel: HomeViewModel = hiltViewModel()
) {

    val state by viewModel.state.collectAsStateWithLifecycle()
    val effect by viewModel.effect.collectAsStateWithLifecycle()

    val context = LocalContext.current

    LaunchedEffect(effect) {
        when (val currentEffect = effect) {
            is HomeContract.HomeEffect.NavigateToDetails -> {
                onNavigateToDetails(currentEffect.item.id)
                viewModel.consumeEffect()
            }

            is HomeContract.HomeEffect.ShowError -> {
                Toast.makeText(context, currentEffect.message, Toast.LENGTH_SHORT).show()
                viewModel.consumeEffect()
            }

            else -> {}
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        if (state.isLoading) {
            MyProgress()
        } else {
            HomeList(state.items) {
                viewModel.handleIntent(
                    HomeContract.HomeIntent.NavigateToDetails(it)
                )
            }
        }
    }
}

@Composable
fun HomeList(items: List<OrderEntity>, onclick: (OrderEntity) -> Unit) {
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(items.size) { index ->
            Card(
                colors = CardColors(
                    containerColor = Color.White,
                    contentColor = Color.White,
                    disabledContainerColor = Color.Gray,
                    disabledContentColor = Color.Gray
                ),
                modifier = Modifier.fillMaxWidth(),
                onClick = {
                    onclick(items[index])
                }
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(Constants.DEFAULT_PADDING),
                ) {
                    MyText(
                        text = "Customer name :  ${items[index].customerName}"
                    )
                    MyText(
                        text = "Restaurant :  ${items[index].restaurantName}"
                    )
                    MyText(
                        text = "Order Status :  ${items[index].orderStatus}"
                    )

                }
            }
        }
    }
}