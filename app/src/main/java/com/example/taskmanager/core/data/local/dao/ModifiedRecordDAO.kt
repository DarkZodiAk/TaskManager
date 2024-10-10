package com.example.taskmanager.core.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import com.example.taskmanager.core.data.local.entity.ModifiedRecordsEntity

@Dao
interface ModifiedRecordDAO {
    @Upsert
    suspend fun upsertItems(vararg items: ModifiedRecordsEntity)

    @Delete
    suspend fun deleteItems(vararg items: ModifiedRecordsEntity)

    @Query("SELECT * FROM modified_records WHERE modificationType = 'UPSERTED'")
    suspend fun getUpsertedItems(): List<ModifiedRecordsEntity>

    @Query("SELECT * FROM modified_records WHERE modificationType = 'DELETED'")
    suspend fun getDeletedItems(): List<ModifiedRecordsEntity>

    @Query("DELETE FROM modified_records")
    suspend fun deleteAllItems()
}