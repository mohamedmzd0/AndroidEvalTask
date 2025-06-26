package com.mohamed.data.network

import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.Protocol
import okhttp3.Response
import okhttp3.ResponseBody.Companion.toResponseBody

class MockInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val path = chain.request().url.encodedPath
        val responseString = when {
            path == "/orders" -> """
            [
                {
                    "id": 1,
                    "clientName": "Mohamed Ali",
                    "restaurantName": "Pizza Corner",
                    "orderStatus": "Delivered"
                },
                {
                    "id": 2,
                    "clientName": "Sara Mahmoud",
                    "restaurantName": "Burger House",
                    "orderStatus": "Preparing"
                },
                {
                    "id": 3,
                    "clientName": "Ahmed Kamal",
                    "restaurantName": "Sushi Star",
                    "orderStatus": "Pending"
                }
            ]
        """.trimIndent()

            path.startsWith("/order/1") -> """
            {
                "id":1,
                "customerName": "Mohamed Ali",
                "orderStatus": "Preparing",
                "progress": 0.6,
                "items": [
                    {
                        "name": "Margherita Pizza",
                        "quantity": 2,
                        "price": 150
                    },
                    {
                        "name": "Soft Drink",
                        "quantity": 3,
                        "price": 45
                    }
                ]
            }
        """.trimIndent()

            path.startsWith("/order/3") -> """
            {
                "id":3,
                "customerName": "Mohamed Ali",
                "orderStatus": "Preparing",
                "progress": 0.6,
                "items": [
                    {
                        "name": "Margherita Pizza",
                        "quantity": 2,
                        "price": 150
                    },
                    {
                        "name": "Soft Drink",
                        "quantity": 3,
                        "price": 45
                    }
                ]
            }
        """.trimIndent()

            else -> "{}"
        }

        return Response.Builder()
            .code(200)
            .message("OK")
            .request(chain.request())
            .protocol(Protocol.HTTP_1_1)
            .body(responseString.toByteArray().toResponseBody("application/json".toMediaType()))
            .addHeader("content-type", "application/json")
            .build()
    }
}
