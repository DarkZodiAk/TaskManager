package com.example.taskmanager.records.presentation.calendar

import com.example.taskmanager.records.data.local.entity.RecordEntity

sealed interface CalendarEvent {
    data class CheckTask(val record: RecordEntity): CalendarEvent
    data class OpenRecord(val id: String): CalendarEvent
    data class ChangeDateTo(val day: Long): CalendarEvent
}