package com.example.taskmanager.core.domain.connectivitymanager

import kotlinx.coroutines.flow.Flow

interface ConnectivityHandler {
    fun isConnected(): Boolean

    fun observeConnectionState(): Flow<ConnectionStatus>
}