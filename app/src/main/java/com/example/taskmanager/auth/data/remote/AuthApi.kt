package com.example.taskmanager.auth.data.remote

import com.example.taskmanager.auth.data.remote.dto.UserDto
import com.example.taskmanager.auth.data.remote.request.LoginRequest
import com.example.taskmanager.auth.data.remote.request.RegisterRequest
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface AuthApi {

    @POST("signup")
    suspend fun register(
        @Body request: RegisterRequest
    )

    @POST("signin")
    suspend fun login(
        @Body request: LoginRequest
    ): UserDto

    @GET("authenticate")
    suspend fun authenticate()
}