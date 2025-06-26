package com.mohamed.data.remote

import com.mohamed.data.model.HomeItemResponse
import com.mohamed.data.model.OrderDetailsResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {

    @GET("orders")
    suspend fun getItems(): List<HomeItemResponse>

    @GET("order/{id}")
    suspend fun getOrderDetails(@Path("id") id: Int): OrderDetailsResponse

}