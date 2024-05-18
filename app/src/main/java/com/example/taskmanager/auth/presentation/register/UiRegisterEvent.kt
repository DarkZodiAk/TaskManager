package com.example.taskmanager.auth.presentation.register

import androidx.annotation.StringRes

sealed interface UiRegisterEvent {
    object OnLogIn:  UiRegisterEvent
    data class ShowErrorMessage(@StringRes val error: Int):  UiRegisterEvent
}