package com.example.taskmanager.core.data.worker

import androidx.work.Constraints
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.NetworkType
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.example.taskmanager.core.domain.worker.SyncRecordsRunner
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class SyncRecordsRunnerImpl @Inject constructor(
    private val workManager: WorkManager
): SyncRecordsRunner {
    override fun run() {
        val deletedRecordsWorkRequest = PeriodicWorkRequestBuilder<SyncDeletedRecordsWorker>(
            repeatInterval = 15L, TimeUnit.MINUTES
        )
            .setConstraints(createConstraints())
            .build()

        val updatedRecordsWorkRequest = PeriodicWorkRequestBuilder<SyncUpdatedRecordsWorker>(
            repeatInterval = 15L, TimeUnit.MINUTES
        )
            .setConstraints(createConstraints())
            .build()

        workManager.enqueueUniquePeriodicWork(
            "sync_deleted_records",
            ExistingPeriodicWorkPolicy.CANCEL_AND_REENQUEUE,
            deletedRecordsWorkRequest
        )

        workManager.enqueueUniquePeriodicWork(
            "sync_updated_records",
            ExistingPeriodicWorkPolicy.CANCEL_AND_REENQUEUE,
            updatedRecordsWorkRequest
        )
    }

    private fun createConstraints() : Constraints {
        return Constraints.Builder()
            .setRequiredNetworkType(
                NetworkType.CONNECTED
            )
            .build()
    }
}