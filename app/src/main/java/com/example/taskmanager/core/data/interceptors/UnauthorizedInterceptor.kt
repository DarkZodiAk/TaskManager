package com.example.taskmanager.core.data.interceptors

import com.example.taskmanager.core.domain.eventBus.AuthEventBus
import com.example.taskmanager.core.domain.eventBus.AuthEventBusEvent
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class UnauthorizedInterceptor @Inject constructor(
    private val authEventBus: AuthEventBus
): Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val original = chain.request()
        val response = chain.proceed(original)
        if(response.code == 401) {
            runBlocking {
                authEventBus.sendEvent(AuthEventBusEvent.Unauthorized)
            }
        }
        return response
    }
}