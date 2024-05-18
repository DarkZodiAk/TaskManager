package com.example.taskmanager.records.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "records")
data class RecordEntity(
    @PrimaryKey(autoGenerate = false)
    val id: String = "",
    val isChecked: Boolean,
    val name: String,
    val description: String,
    val isTask: Boolean,
    //val urgency: Urgency,
    val deadline: Long
)
