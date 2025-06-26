package com.mohamed.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.mohamed.data.model.HomeItemResponse
import kotlinx.coroutines.flow.Flow

@Dao
interface OrderDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrders(orders: List<HomeItemResponse>)

    @Query("SELECT * FROM orders")
    suspend fun getOrders(): List<HomeItemResponse>

    @Query("DELETE FROM orders")
    suspend fun clearOrders()
}