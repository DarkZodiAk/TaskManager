package com.example.taskmanager.records.presentation.records

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.taskmanager.records.domain.repository.RecordRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RecordsViewModel @Inject constructor(
    private val recordRepository: RecordRepository
) : ViewModel() {

    val records = recordRepository.getRecords()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), emptyList())

    private val channel = Channel<UiRecordsEvent>()
    val uiEvent = channel.receiveAsFlow()

    fun onEvent(event: RecordsEvent) {
        when(event) {
            is RecordsEvent.CheckTask -> {
                viewModelScope.launch {
                    recordRepository.upsertRecord(event.record.copy(isChecked = !event.record.isChecked))
                }
            }
            is RecordsEvent.OpenRecord -> {
                viewModelScope.launch {
                    channel.send(UiRecordsEvent.OnOpenRecord(event.id))
                }
            }
        }
    }
}