package com.example.taskmanager.records.presentation.editRecord

import java.time.LocalDateTime
import java.util.UUID

data class EditRecordState(
    val id: String = UUID.randomUUID().toString().replace("-", "").substring(0,24),
    val isChecked: Boolean = false,
    val name: String = "",
    val description: String = "",
    val isTask: Boolean = true,
    val deadline: LocalDateTime? = null,
    val isDialogOpen: Boolean = false
)
