package com.example.taskmanager.auth.presentation.login

sealed interface LoginEvent {
    data class EmailChanged(val value: String): LoginEvent
    data class PasswordChanged(val value: String): LoginEvent
    object Login: LoginEvent
    object SignUp: LoginEvent
}