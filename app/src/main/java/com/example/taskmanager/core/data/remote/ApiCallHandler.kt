package com.example.taskmanager.core.data.remote

import java.util.concurrent.CancellationException

suspend fun <T> safeSuspendCall(call: suspend () -> T): Result<T> {
    return try {
        Result.success(call.invoke())
    } catch(e: CancellationException) {
        throw e
    } catch(e: Exception) {
        e.printStackTrace()
        Result.failure(e)
    }
}