package com.example.taskmanager.core.di

import com.example.taskmanager.core.data.connectivitymanager.ConnectivityHandlerImpl
import com.example.taskmanager.core.domain.connectivitymanager.ConnectivityHandler
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class ConnectivityHandlerModule {
    @Binds
    @Singleton
    abstract fun bindConnectivityHandlerImpl(
        connectivityHandlerImpl: ConnectivityHandlerImpl
    ): ConnectivityHandler
}