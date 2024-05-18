package com.example.taskmanager.records.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.taskmanager.records.data.local.dao.ModifiedRecordDAO
import com.example.taskmanager.records.data.local.dao.RecordDAO
import com.example.taskmanager.records.data.local.entity.ModifiedRecordsEntity
import com.example.taskmanager.records.data.local.entity.RecordEntity

@Database(
    entities = [RecordEntity::class, ModifiedRecordsEntity::class],
    version = 1
)
@TypeConverters(Converters::class)
abstract class RecordDatabase: RoomDatabase() {
    abstract fun recordDao(): RecordDAO

    abstract fun modifiedRecordDAO(): ModifiedRecordDAO
}