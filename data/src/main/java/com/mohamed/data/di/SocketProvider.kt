package com.mohamed.data.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.socket.client.IO
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object SocketProvider {

    @Provides
    @Singleton
    fun provideSocket(): io.socket.client.Socket {
        return IO.socket("http://10.0.2.2:3000")
    }

}