package com.example.taskmanager.core.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.taskmanager.core.data.local.model.ModificationType

@Entity(tableName = "modified_records")
data class ModifiedRecordsEntity(
    @PrimaryKey(autoGenerate = false)
    val id: String,
    val modificationType: ModificationType
)
