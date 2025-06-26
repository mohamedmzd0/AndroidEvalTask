package com.mohamed.data.di

import android.content.Context
import androidx.room.Room
import com.mohamed.data.local.AppDatabase
import com.mohamed.data.local.OrderDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "orders_db"
        ).build()
    }

    @Provides
    fun provideOrderDao(db: AppDatabase): OrderDao = db.orderDao()
}