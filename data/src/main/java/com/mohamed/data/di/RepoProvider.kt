package com.mohamed.data.di

import com.mohamed.data.repo.OrdersRepositoryImpl
import com.mohamed.domain.repo.OrdersRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface RepoProvider {


    @Binds
    @Singleton
    fun provideRepo(
        repo: OrdersRepositoryImpl
    ): OrdersRepository

}
