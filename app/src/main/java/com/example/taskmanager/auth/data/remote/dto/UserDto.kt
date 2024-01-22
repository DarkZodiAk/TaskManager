package com.example.taskmanager.auth.data.remote.dto

import com.example.taskmanager.core.domain.model.User
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class UserDto(
    val token: String,
    val username: String
)

fun UserDto.toUser(): User {
    return User(
        token = token,
        username = username,
        email = ""
    )
}
