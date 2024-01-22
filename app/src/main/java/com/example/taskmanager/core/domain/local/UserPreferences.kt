package com.example.taskmanager.core.domain.local

import com.example.taskmanager.core.domain.model.User

interface UserPreferences {
    fun saveUser(user: User)
    fun getUser(): User?
    fun isLoggedIn(): Boolean
    fun clear()

    companion object {
        const val TOKEN = "TOKEN"
        const val USERNAME = "USERNAME"
        const val EMAIL = "EMAIL"
    }
}