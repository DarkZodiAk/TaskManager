package com.example.taskmanager.records.presentation.editRecord

sealed interface UiEditRecordEvent {
    object OnBack: UiEditRecordEvent
}