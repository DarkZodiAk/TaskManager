package com.example.taskmanager.auth.domain.usecase

import com.example.taskmanager.auth.domain.matcher.PasswordMatcher
import com.example.taskmanager.auth.domain.matcher.PasswordValidationResult

class ValidatePasswordUseCase(
    private val matcher: PasswordMatcher
) {
    operator fun invoke(password: String): PasswordValidationResult {
        return matcher.matches(password)
    }
}