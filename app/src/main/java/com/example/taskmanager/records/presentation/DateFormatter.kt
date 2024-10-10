package com.example.taskmanager.records.presentation

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.example.taskmanager.R
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@Composable
fun LocalDateTime.string(): String {
    val currentDateTime = LocalDateTime.now()
    val time = format(DateTimeFormatter.ofPattern("HH:mm"))
    return when {
        this.toLocalDate() == currentDateTime.toLocalDate().minusDays(1) -> stringResource(id = R.string.yesterday) + ", $time"
        this.toLocalDate() == currentDateTime.toLocalDate() -> stringResource(id = R.string.today) + ", $time"
        this.toLocalDate() == currentDateTime.toLocalDate().plusDays(1) -> stringResource(id = R.string.tomorrow) + ", $time"
        else -> format(DateTimeFormatter.ofPattern("MM.dd HH:mm"))
    }
}