package com.example.taskmanager.records.domain.usecase

import com.example.taskmanager.records.domain.worker.LoadRecordsRunner
import javax.inject.Inject

class PeriodicLoadRecordsUseCase @Inject constructor(
    private val loadRecordsRunner: LoadRecordsRunner
) {
    operator fun invoke() {
        loadRecordsRunner.run()
    }
}