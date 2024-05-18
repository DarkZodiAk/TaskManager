package com.example.taskmanager.core.domain.usecase

import com.example.taskmanager.records.data.local.RecordDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ClearDatabaseUseCase @Inject constructor(
    private val db: RecordDatabase
) {
    suspend operator fun invoke() {
        withContext(Dispatchers.IO) {
            db.clearAllTables()
        }
    }
}