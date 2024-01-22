package com.example.taskmanager.auth.domain.repository

import com.example.taskmanager.core.domain.model.User

interface AuthRepository {

    suspend fun register(email: String, username: String, password: String): Result<Unit>

    suspend fun login(email: String, password: String): Result<User>

    suspend fun authenticate(): Result<Unit>
}