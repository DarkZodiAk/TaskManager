package com.example.taskmanager.auth.domain.usecase

import com.example.taskmanager.auth.domain.matcher.EmailMatcher

class ValidateEmailUseCase(
    private val matcher: EmailMatcher
) {
    operator fun invoke(email: String): Boolean {
        return matcher.matches(email)
    }
}