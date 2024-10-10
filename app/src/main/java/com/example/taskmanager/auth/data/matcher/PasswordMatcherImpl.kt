package com.example.taskmanager.auth.data.matcher

import com.example.taskmanager.auth.domain.matcher.PasswordMatcher
import com.example.taskmanager.auth.domain.matcher.PasswordValidationResult
import javax.inject.Inject

class PasswordMatcherImpl @Inject constructor(): PasswordMatcher {
    override fun matches(password: String): PasswordValidationResult {
        if(password.length < 8){
            return PasswordValidationResult.TOO_SHORT
        }
        if(!password.any { it.isLowerCase() }) {
            return PasswordValidationResult.NO_LOWERCASE
        }
        if(!password.any { it.isUpperCase() }) {
            return PasswordValidationResult.NO_UPPERCASE
        }
        if(!password.any { it.isDigit() }) {
            return PasswordValidationResult.NO_DIGIT
        }
        return PasswordValidationResult.SUCCESS
    }
}