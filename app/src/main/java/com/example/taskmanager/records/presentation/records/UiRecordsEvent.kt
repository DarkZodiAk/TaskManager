package com.example.taskmanager.records.presentation.records

sealed interface UiRecordsEvent {
    data class OnOpenRecord(val id: String): UiRecordsEvent
}