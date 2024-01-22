package com.example.taskmanager.auth.domain.usecase

import com.example.taskmanager.auth.domain.repository.AuthRepository
import com.example.taskmanager.core.domain.model.User

class LoginUseCase(
    private val repository: AuthRepository
) {
    suspend operator fun invoke(email: String, password: String): Result<User> {
        return repository.login(email, password)
    }
}