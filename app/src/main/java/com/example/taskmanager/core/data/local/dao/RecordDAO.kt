package com.example.taskmanager.core.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import com.example.taskmanager.core.data.local.entity.RecordEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface RecordDAO {

    @Upsert
    suspend fun upsertRecords(vararg record: RecordEntity)

    @Delete
    suspend fun deleteRecords(vararg records: RecordEntity)

    @Query("SELECT * FROM records WHERE id = :id")
    suspend fun getRecordById(id: String): RecordEntity

    @Query("SELECT * FROM records")
    fun getRecords(): Flow<List<RecordEntity>>

    @Query("SELECT * FROM records WHERE deadline BETWEEN :from AND :to")
    fun getRecordsForDay(from: Long, to: Long): Flow<List<RecordEntity>>

    @Query("DELETE FROM records")
    suspend fun deleteAllRecords()
}