package com.example.taskmanager.records.presentation.editRecord.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TimePicker
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.taskmanager.R
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DateTimePickerDialog(
    isDialogOpen: Boolean,
    onDateSelected: (LocalDateTime) -> Unit,
    onDismiss: () -> Unit
) {
    val datePickerState = rememberDatePickerState()
    val timePickerState = rememberTimePickerState(is24Hour = true)


    if(isDialogOpen) {
        val confirmEnabled by remember { derivedStateOf { datePickerState.selectedDateMillis != null} }
        var isDatePickerOpen by remember {
            mutableStateOf(true)
        }

        DatePickerDialog(
            onDismissRequest = onDismiss,
            confirmButton = {
                Row {
                    if(isDatePickerOpen) {
                        Button(
                            onClick = { isDatePickerOpen = false },
                            enabled = confirmEnabled
                        ) {
                            Text(text = stringResource(id = R.string.next))
                        }
                    }
                    else {
                        OutlinedButton(onClick = { isDatePickerOpen = true }) {
                            Text(text = stringResource(id = R.string.back))
                        }
                        Spacer(modifier = Modifier.width(4.dp))
                        Button(
                            onClick = {
                                if(datePickerState.selectedDateMillis != null){
                                    val instant = Instant.ofEpochMilli(datePickerState.selectedDateMillis!!)
                                    var date = LocalDateTime.ofInstant(instant, ZoneId.systemDefault())
                                    date = date.plusHours(timePickerState.hour.toLong())
                                    date = date.plusMinutes(timePickerState.minute.toLong())
                                    onDateSelected(date)
                                }
                            },
                            enabled = confirmEnabled
                        ) {
                            Text(text = stringResource(id = R.string.save))
                        }
                    }
                }
            }
        ) {
            if(isDatePickerOpen)
                DatePicker(state = datePickerState)
            else
                TimePicker(
                    state = timePickerState,
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .padding(top = 8.dp)
                )
        }
    }
}