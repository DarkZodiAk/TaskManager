package com.example.taskmanager.auth.domain.usecase

data class RegisterUseCases(
    val registerUseCase: RegisterUseCase,
    val validatePasswordUseCase: ValidatePasswordUseCase,
    val validateEmailUseCase: ValidateEmailUseCase,
    val validateNameUseCase: ValidateNameUseCase
)
