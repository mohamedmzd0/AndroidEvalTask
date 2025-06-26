package com.mohamed.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.mohamed.data.model.HomeItemResponse

@Database(entities = [HomeItemResponse::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun orderDao(): OrderDao
}