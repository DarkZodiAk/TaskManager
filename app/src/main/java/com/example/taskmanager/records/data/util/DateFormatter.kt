package com.example.taskmanager.records.data.util

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

fun LocalDateTime.string(): String {
    val currentDateTime = LocalDateTime.now()
    val time = format(DateTimeFormatter.ofPattern("HH:mm"))
    return when {
        this.toLocalDate() == currentDateTime.toLocalDate().minusDays(1) -> "Вчера, $time"
        this.toLocalDate() == currentDateTime.toLocalDate() -> "Сегодня, $time"
        this.toLocalDate() == currentDateTime.toLocalDate().plusDays(1) -> "Завтра, $time"
        else -> format(DateTimeFormatter.ofPattern("MM.dd HH:mm"))
    }
}