package com.example.taskmanager.records.presentation.records

import com.example.taskmanager.core.data.local.entity.RecordEntity

sealed interface RecordsEvent {
    data class CheckTask(val record: RecordEntity): RecordsEvent
    data class OpenRecord(val id: String): RecordsEvent
}