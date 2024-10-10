package com.example.taskmanager.records.presentation.calendar

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.taskmanager.core.data.local.entity.RecordEntity
import com.example.taskmanager.records.data.util.LocalDateTimeConverter
import com.example.taskmanager.core.domain.repository.RecordRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import java.time.Instant
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.ZoneId
import javax.inject.Inject

@HiltViewModel
class CalendarViewModel @Inject constructor(
    private val recordRepository: RecordRepository
) : ViewModel() {

    var records by mutableStateOf(emptyList<RecordEntity>())
        private set

    private val channel = Channel<UiCalendarEvent>()
    val uiEvent = channel.receiveAsFlow()

    private var currentDayJob: Job? = null

    init {
        getRecordsForChosenDay(LocalDateTimeConverter.getCurrentEpoch())
    }

    fun onEvent(event: CalendarEvent) {
        when(event) {
            is CalendarEvent.ChangeDateTo -> {
                getRecordsForChosenDay(event.day)
            }
            is CalendarEvent.CheckTask -> {
                viewModelScope.launch {
                    recordRepository.upsertRecord(event.record.copy(isChecked = !event.record.isChecked))
                }
            }
            is CalendarEvent.OpenRecord -> {
                viewModelScope.launch {
                    channel.send(UiCalendarEvent.OnOpenRecord(event.id))
                }
            }
        }
    }

    private fun getRecordsForChosenDay(day: Long) {
        val date = Instant.ofEpochMilli(day).atZone(ZoneId.systemDefault()).toLocalDate()
        val from = LocalDateTimeConverter.getEpochForUTC(date.atStartOfDay())
        val to = LocalDateTimeConverter.getEpochForUTC(
            LocalDateTime.of(date, LocalTime.of(23, 59))
        )

        currentDayJob?.cancel()
        currentDayJob = viewModelScope.launch {
            recordRepository.getRecordsForDay(from, to)
                .collectLatest {
                    records = it
                }
        }
    }
}