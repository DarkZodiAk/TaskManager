package com.example.taskmanager.core.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.taskmanager.core.data.local.dao.ModifiedRecordDAO
import com.example.taskmanager.core.data.local.dao.RecordDAO
import com.example.taskmanager.core.data.local.entity.ModifiedRecordsEntity
import com.example.taskmanager.core.data.local.entity.RecordEntity

@Database(
    entities = [RecordEntity::class, ModifiedRecordsEntity::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class RecordDatabase: RoomDatabase() {
    abstract fun recordDao(): RecordDAO
    abstract fun modifiedRecordDAO(): ModifiedRecordDAO


}