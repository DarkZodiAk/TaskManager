package com.example.taskmanager.auth.domain.matcher

interface PasswordMatcher {
    fun matches(password: String): PasswordValidationResult
}