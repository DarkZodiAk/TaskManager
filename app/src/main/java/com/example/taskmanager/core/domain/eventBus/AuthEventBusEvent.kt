package com.example.taskmanager.core.domain.eventBus

sealed interface AuthEventBusEvent {
    object LogIn: AuthEventBusEvent
    object LogOut: AuthEventBusEvent
    object Unauthorized: AuthEventBusEvent
}