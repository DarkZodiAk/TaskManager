package com.example.taskmanager.records.data.worker

import androidx.work.Constraints
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.NetworkType
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.example.taskmanager.records.domain.worker.LoadRecordsRunner
import java.util.concurrent.TimeUnit

class LoadRecordsRunnerImpl(
    private val workManager: WorkManager
): LoadRecordsRunner {
    override fun run() {
        val workRequest = PeriodicWorkRequestBuilder<LoadRecordsWorker>(
            repeatInterval = 15L, TimeUnit.MINUTES
        )
            .setConstraints(createConstraints())
            .build()

        workManager.enqueueUniquePeriodicWork(
            "update_records",
            ExistingPeriodicWorkPolicy.CANCEL_AND_REENQUEUE,
            workRequest
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