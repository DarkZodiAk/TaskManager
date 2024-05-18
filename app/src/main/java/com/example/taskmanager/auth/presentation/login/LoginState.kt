package com.example.taskmanager.auth.presentation.login

import androidx.annotation.StringRes
import com.example.taskmanager.R

data class LoginState(
    val isLoading: Boolean = false,
    val email: String = "",
    val password: String = "",
    val isEmailValid: Boolean = false,
    val isError: Boolean = false,
    @StringRes val emailLabel: Int = R.string.email_label,
    @StringRes val passwordLabel: Int = R.string.password_label
)
