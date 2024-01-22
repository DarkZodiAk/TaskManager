package com.example.taskmanager.core.domain.usecase

import com.example.taskmanager.auth.domain.usecase.AuthenticateUseCase
import javax.inject.Inject

data class MainUseCases @Inject constructor(
    val authenticateUseCase: AuthenticateUseCase
)