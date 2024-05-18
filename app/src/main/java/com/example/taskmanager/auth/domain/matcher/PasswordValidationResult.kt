package com.example.taskmanager.auth.domain.matcher

enum class PasswordValidationResult {
    SUCCESS,
    TOO_SHORT,
    NO_LOWERCASE,
    NO_UPPERCASE,
    NO_DIGIT
}