package com.example.taskmanager.records.data.worker

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.taskmanager.core.data.remote.safeSuspendCall
import com.example.taskmanager.records.data.local.dao.ModifiedRecordDAO
import com.example.taskmanager.records.data.remote.RecordApi
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.NonCancellable
import kotlinx.coroutines.joinAll
import kotlinx.coroutines.launch
import kotlinx.coroutines.supervisorScope
import kotlinx.coroutines.withContext
import retrofit2.HttpException

@HiltWorker
class SyncDeletedRecordsWorker @AssistedInject constructor(
    @Assisted private val context: Context,
    @Assisted private val params: WorkerParameters,
    private val modifiedRecordDAO: ModifiedRecordDAO,
    private val api: RecordApi
) : CoroutineWorker(context, params) {
    override suspend fun doWork(): Result {
        if(runAttemptCount >= 3)
            return Result.failure()

        val deletedRecords = modifiedRecordDAO.getDeletedItems()

        if(deletedRecords.isEmpty())
            return Result.success()

        val result = safeSuspendCall {
            supervisorScope {
                val jobsList = deletedRecords.map {
                    launch {
                        api.deleteRecord(it.id)
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
                if (exception is HttpException && exception.code() == 401) Result.failure()
                else Result.retry()
            }
            Result.retry()
        }
    }
}