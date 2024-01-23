package com.example.taskmanager.auth.data.repository

import com.example.taskmanager.auth.data.remote.AuthApi
import com.example.taskmanager.auth.data.remote.dto.toUser
import com.example.taskmanager.auth.data.remote.request.LoginRequest
import com.example.taskmanager.auth.data.remote.request.RegisterRequest
import com.example.taskmanager.auth.domain.repository.AuthRepository
import com.example.taskmanager.core.data.remote.safeSuspendCall
import com.example.taskmanager.core.domain.model.User

class AuthRepositoryImpl(
    private val api: AuthApi
): AuthRepository {
    override suspend fun register(email: String, username: String, password: String): Result<Unit> {
        return safeSuspendCall {
            api.register(
                RegisterRequest(email, username, password)
            )
        }
    }

    override suspend fun login(email: String, password: String): Result<User> {
        return safeSuspendCall {
            api.login(
                LoginRequest(email, password)
            ).toUser()
        }
    }

    override suspend fun authenticate(): Result<Unit> {
        return safeSuspendCall {
            api.authenticate()
        }
    }

    override suspend fun logout(): Result<Unit> {
        return safeSuspendCall {
            api.logout()
        }
    }
}