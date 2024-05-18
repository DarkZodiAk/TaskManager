package com.example.taskmanager.auth.presentation.register

import androidx.annotation.StringRes
import com.example.taskmanager.R

data class RegisterState(
    val isLoading: Boolean = false,
    val name: String = "",
    val email: String = "",
    val password: String = "",
    val isEmailValid: Boolean = false,
    val isError: Boolean = false,
    @StringRes val emailLabel: Int = R.string.email_label,
    @StringRes val passwordLabel: Int = R.string.password_label,
    @StringRes val nameLabel: Int = R.string.name_label
)
