package com.example.taskmanager.core.domain.eventBus

import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow

class AuthEventBus {
    private val _authBus = MutableSharedFlow<AuthEventBusEvent>()
    val authBus = _authBus.asSharedFlow()

    suspend fun sendEvent(event: AuthEventBusEvent) {
        _authBus.emit(event)
    }
}