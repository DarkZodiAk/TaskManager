package com.example.taskmanager.auth.presentation.login

import androidx.annotation.StringRes

sealed interface UiLoginEvent {
    object OnSignUp: UiLoginEvent
    data class ShowErrorMessage(@StringRes val error: Int): UiLoginEvent
}