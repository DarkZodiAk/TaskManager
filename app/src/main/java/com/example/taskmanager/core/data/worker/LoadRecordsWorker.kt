package com.example.taskmanager.core.data.worker

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.taskmanager.core.domain.repository.RecordRepository
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import retrofit2.HttpException

@HiltWorker
class LoadRecordsWorker @AssistedInject constructor(
    @Assisted private val context: Context,
    @Assisted private val params: WorkerParameters,
    private val recordRepository: RecordRepository
) : CoroutineWorker(context, params) {
    override suspend fun doWork(): Result {
        if(runAttemptCount >= 3)
            return Result.failure()
        val result = recordRepository.updateRecords()
        return if(result.isSuccess)
            Result.success()
        else {
            result.exceptionOrNull()?.let { exception ->
                if (exception is HttpException && exception.code() == 401) Result.failure()
                else Result.retry()
            }
            Result.retry()
        }
    }
}