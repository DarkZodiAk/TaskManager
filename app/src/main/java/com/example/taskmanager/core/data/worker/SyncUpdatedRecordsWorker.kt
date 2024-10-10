package com.example.taskmanager.core.data.worker

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.taskmanager.core.data.remote.safeSuspendCall
import com.example.taskmanager.core.data.local.dao.ModifiedRecordDAO
import com.example.taskmanager.records.data.remote.RecordApi
import com.example.taskmanager.records.data.remote.dto.toRecordDto
import com.example.taskmanager.core.domain.repository.RecordRepository
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.NonCancellable
import kotlinx.coroutines.joinAll
import kotlinx.coroutines.launch
import kotlinx.coroutines.supervisorScope
import kotlinx.coroutines.withContext
import retrofit2.HttpException

@HiltWorker
class SyncUpdatedRecordsWorker @AssistedInject constructor(
    @Assisted private val context: Context,
    @Assisted private val params: WorkerParameters,
    private val modifiedRecordDAO: ModifiedRecordDAO,
    private val recordRepository: RecordRepository,
    private val api: RecordApi
) : CoroutineWorker(context, params) {
    override suspend fun doWork(): Result {
        if(runAttemptCount >= 3)
            return Result.failure()

        val updatedRecords = modifiedRecordDAO.getUpsertedItems()

        if(updatedRecords.isEmpty())
            return Result.success()

        val result = safeSuspendCall {
            supervisorScope {
                val jobsList = updatedRecords.map {
                    launch {
                        api.saveRecord(recordRepository.getRecordById(it.id).toRecordDto())
                        withContext(NonCancellable) {
                            modifiedRecordDAO.deleteItems(it)
                        }
                    }
                }
                jobsList.joinAll()
            }
        }

        return if(result.isSuccess)
            Result.success()
        else {
            result.exceptionOrNull()?.let { exception ->
                if(exception is HttpException && exception.code() == 401) Result.failure()
                else Result.retry()
            }
            Result.retry()
        }
    }
}