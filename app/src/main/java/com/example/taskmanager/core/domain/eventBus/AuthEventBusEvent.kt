package com.example.taskmanager.core.domain.eventBus

sealed interface AuthEventBusEvent {
    object Login: AuthEventBusEvent
    object Logout: AuthEventBusEvent
    object Unauthorized: AuthEventBusEvent
}