package com.example.taskmanager.auth.presentation.register

sealed interface RegisterEvent {
    data class NameChanged(val value: String): RegisterEvent
    data class EmailChanged(val value: String): RegisterEvent
    data class PasswordChanged(val value: String): RegisterEvent
    object Register: RegisterEvent
    object Login: RegisterEvent
}