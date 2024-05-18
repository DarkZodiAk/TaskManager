package com.example.taskmanager.auth.domain.usecase

data class LoginUseCases(
    val loginUseCase: LoginUseCase,
    val validateEmailUseCase: ValidateEmailUseCase,
    val validatePasswordUseCase: ValidatePasswordUseCase
)