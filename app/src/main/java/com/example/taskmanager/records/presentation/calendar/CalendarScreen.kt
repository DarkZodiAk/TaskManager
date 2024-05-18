@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.taskmanager.records.presentation.calendar

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.DatePicker
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Scaffold
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.taskmanager.records.data.util.LocalDateTimeConverter
import com.example.taskmanager.records.presentation.components.RecordsNavbar
import com.example.taskmanager.records.presentation.components.RecordCard

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CalendarScreen(
    viewModel: CalendarViewModel = hiltViewModel(),
    navigate: (String) -> Unit,
    onOpenRecord: (String) -> Unit
) {
    val records = viewModel.records

    val datePickerState = rememberDatePickerState(
        initialSelectedDateMillis = LocalDateTimeConverter.getCurrentEpoch()
    )

    LaunchedEffect(datePickerState.selectedDateMillis) {
        viewModel.onEvent(CalendarEvent.ChangeDateTo(datePickerState.selectedDateMillis!!))
    }

    LaunchedEffect(true) {
        viewModel.uiEvent.collect { event ->
            when(event) {
                is UiCalendarEvent.OnOpenRecord -> onOpenRecord(event.id)
            }
        }
    }

    Scaffold(
        bottomBar = {
            RecordsNavbar(
                initialIndex = 1,
                onClick = navigate
            )
        }
    ) {
        Column(
            modifier = Modifier
                .padding(it)
                .fillMaxSize()
        ) {
            Spacer(modifier = Modifier.height(8.dp))
            DatePicker(
                state = datePickerState,
                title = null,
                headline = null,
                showModeToggle = false
            )
            HorizontalDivider(modifier = Modifier.fillMaxWidth())
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                items(records) { record ->
                    Spacer(Modifier.height(8.dp))
                    RecordCard(
                        title = record.name,
                        isChecked = record.isChecked,
                        isTask = record.isTask,
                        //urgency = record.urgency,
                        deadline =
                            if(record.deadline > 0L) LocalDateTimeConverter.longToLocalDateTimeWithTimezone(record.deadline) else null,
                        onCheck = { viewModel.onEvent(CalendarEvent.CheckTask(record)) },
                        onClick = { viewModel.onEvent(CalendarEvent.OpenRecord(record.id)) },
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }
        }
    }
}