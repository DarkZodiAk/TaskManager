package com.example.taskmanager.auth.domain.usecase

class ValidateNameUseCase {
    operator fun invoke(username: String): Boolean {
        return username.length in 4..50
    }
}