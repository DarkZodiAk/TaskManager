package com.example.taskmanager.records.domain.usecase

import com.example.taskmanager.core.domain.worker.SyncRecordsRunner
import javax.inject.Inject

class PeriodicSyncRecordsUseCase @Inject constructor(
    private val syncRecordsRunner: SyncRecordsRunner
) {
    operator fun invoke() {
        syncRecordsRunner.run()
    }
}