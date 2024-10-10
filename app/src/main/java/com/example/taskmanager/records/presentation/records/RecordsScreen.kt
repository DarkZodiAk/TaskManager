package com.example.taskmanager.records.presentation.records

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.taskmanager.R
import com.example.taskmanager.records.data.util.LocalDateTimeConverter
import com.example.taskmanager.records.presentation.components.RecordCard
import com.example.taskmanager.records.presentation.components.RecordsNavbar

@Composable
fun RecordsScreen(
    viewModel: RecordsViewModel = hiltViewModel(),
    navigate: (String) -> Unit,
    onOpenRecord: (String) -> Unit
) {
    val records = viewModel.records.collectAsState()

    LaunchedEffect(true) {
        viewModel.uiEvent.collect{ event ->
            when(event) {
                is UiRecordsEvent.OnOpenRecord -> onOpenRecord(event.id)
            }
        }
    }

    Scaffold(
        bottomBar = {
            RecordsNavbar(
                initialIndex = 0,
                onClick = navigate
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    viewModel.onEvent(RecordsEvent.OpenRecord("nul"))
                },
                containerColor = MaterialTheme.colorScheme.primary
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = stringResource(id = R.string.add_note),
                    tint = MaterialTheme.colorScheme.onPrimary
                )
            }
        }
    ) {
        LazyColumn(
            modifier = Modifier
                .padding(it)
                .fillMaxSize()
                .padding(16.dp)
        ) {
            items(records.value) { record ->
                Spacer(Modifier.height(8.dp))
                RecordCard(
                    title = record.name,
                    isChecked = record.isChecked,
                    isTask = record.isTask,
                    deadline =
                        if(record.deadline > 0L) LocalDateTimeConverter.longToLocalDateTimeWithTimezone(record.deadline) else null,
                    onCheck = { viewModel.onEvent(RecordsEvent.CheckTask(record)) },
                    onClick = { viewModel.onEvent(RecordsEvent.OpenRecord(record.id)) },
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }

}