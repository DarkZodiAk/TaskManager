package com.example.taskmanager.auth.domain.usecase

import com.example.taskmanager.auth.domain.repository.AuthRepository

class RegisterUseCase(
    private val repository: AuthRepository
) {
    suspend operator fun invoke(email: String, username: String, password: String): Result<Unit> {
        return repository.register(email, username, password)
    }
}