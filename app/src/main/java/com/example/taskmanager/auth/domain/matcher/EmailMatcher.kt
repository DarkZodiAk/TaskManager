package com.example.taskmanager.auth.domain.matcher

interface EmailMatcher {
    fun matches(email: String): Boolean
}