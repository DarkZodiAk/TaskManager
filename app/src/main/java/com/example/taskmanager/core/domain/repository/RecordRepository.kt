package com.example.taskmanager.core.domain.repository

import com.example.taskmanager.core.data.local.entity.RecordEntity
import kotlinx.coroutines.flow.Flow

interface RecordRepository {

    suspend fun getRecordById(id: String): RecordEntity

    fun getRecords(): Flow<List<RecordEntity>>

    suspend fun getRecordsForDay(from: Long, to: Long): Flow<List<RecordEntity>>

    suspend fun deleteRecord(record: RecordEntity)

    suspend fun upsertRecord(record: RecordEntity)

    suspend fun updateRecords(): Result<Unit>

    suspend fun clearRecords()

}