package com.example.taskmanager.core.data.repository

import com.example.taskmanager.core.data.remote.safeSuspendCall
import com.example.taskmanager.core.data.local.dao.ModifiedRecordDAO
import com.example.taskmanager.core.data.local.dao.RecordDAO
import com.example.taskmanager.core.data.local.entity.ModifiedRecordsEntity
import com.example.taskmanager.core.data.local.entity.RecordEntity
import com.example.taskmanager.records.data.remote.RecordApi
import com.example.taskmanager.records.data.remote.dto.toRecord
import com.example.taskmanager.records.data.remote.dto.toRecordDto
import com.example.taskmanager.core.data.local.model.ModificationType
import com.example.taskmanager.core.domain.repository.RecordRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.joinAll
import kotlinx.coroutines.launch
import kotlinx.coroutines.supervisorScope
import javax.inject.Inject

class RecordRepositoryImpl @Inject constructor(
    private val recordDAO: RecordDAO,
    private val modifiedRecordDAO: ModifiedRecordDAO,
    private val api: RecordApi
): RecordRepository {
    override suspend fun getRecordById(id: String): RecordEntity {
        return recordDAO.getRecordById(id)
    }

    override fun getRecords(): Flow<List<RecordEntity>> {
        return recordDAO.getRecords()
    }

    override suspend fun getRecordsForDay(from: Long, to: Long): Flow<List<RecordEntity>> {
        return recordDAO.getRecordsForDay(from, to)
            .map { it.sortedBy { it.deadline } }
    }

    override suspend fun deleteRecord(record: RecordEntity) {
        recordDAO.deleteRecords(record)
        safeSuspendCall {
            api.deleteRecord(record.id)
        }.onFailure {
            modifiedRecordDAO.upsertItems(
                ModifiedRecordsEntity(
                    id = record.id,
                    modificationType = ModificationType.DELETED
                )
            )
        }
    }

    override suspend fun upsertRecord(record: RecordEntity) {
        recordDAO.upsertRecords(record)
        safeSuspendCall {
            api.saveRecord(record.toRecordDto())
        }.onFailure {
            modifiedRecordDAO.upsertItems(
                ModifiedRecordsEntity(
                    id = record.id,
                    modificationType = ModificationType.UPSERTED
                )
            )
        }
    }

    override suspend fun updateRecords(): Result<Unit> {
        return safeSuspendCall {
            val result = api.getRecords()
            supervisorScope {
                val jobsList = result.records.map { recordDto ->
                    launch { recordDAO.upsertRecords(recordDto.toRecord()) }
                }
                jobsList.joinAll()
            }
        }
    }

    override suspend fun clearRecords() {
        recordDAO.deleteAllRecords()
        modifiedRecordDAO.deleteAllItems()
    }
}