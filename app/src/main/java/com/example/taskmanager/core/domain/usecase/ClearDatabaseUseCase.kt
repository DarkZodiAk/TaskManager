package com.example.taskmanager.core.domain.usecase

import com.example.taskmanager.core.domain.repository.RecordRepository
import javax.inject.Inject

class ClearDatabaseUseCase @Inject constructor(
    private val repository: RecordRepository
) {
    suspend operator fun invoke() {
        repository.clearRecords()
    }
}