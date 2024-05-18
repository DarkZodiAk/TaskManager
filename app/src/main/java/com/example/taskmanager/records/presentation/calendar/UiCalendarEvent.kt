package com.example.taskmanager.records.presentation.calendar

sealed interface UiCalendarEvent {
    data class OnOpenRecord(val id: String): UiCalendarEvent
}