package com.example.taskmanager.core.data.interceptors

import com.example.taskmanager.core.domain.local.UserPreferences
import okhttp3.Interceptor
import okhttp3.Response

class AuthTokenInterceptor(
    private val preferences: UserPreferences
): Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val original = chain.request()
        val token = preferences.getUser()?.token
        val request = if(token != null){
            original
                .newBuilder()
                .addHeader("Authorization", "Bearer $token")
                .build()
        } else {
            original
        }
        return chain.proceed(request)
    }
}