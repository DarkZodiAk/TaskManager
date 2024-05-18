package com.example.taskmanager.records.presentation.editRecord

import java.time.LocalDateTime

sealed interface EditRecordEvent {
    data class UpdateName(val value: String): EditRecordEvent
    data class UpdateDesc(val value: String): EditRecordEvent
    object OnBack: EditRecordEvent
    object DeleteRecord: EditRecordEvent
    object ChangeType: EditRecordEvent
    object Check: EditRecordEvent
    object OpenTimePickerDialog: EditRecordEvent
    object CloseTimePickerDialog: EditRecordEvent
    object ClearDeadline: EditRecordEvent
    object ChangeRecordType: EditRecordEvent
    data class SaveDeadline(val date: LocalDateTime): EditRecordEvent
}