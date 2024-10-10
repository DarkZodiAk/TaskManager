package com.example.taskmanager.records.presentation.editRecord

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.taskmanager.core.data.local.entity.RecordEntity
import com.example.taskmanager.records.data.util.LocalDateTimeConverter
import com.example.taskmanager.core.domain.repository.RecordRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EditRecordViewModel @Inject constructor(
    private val recordRepository: RecordRepository,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {
    var state by mutableStateOf(EditRecordState())
        private set

    private val channel = Channel<UiEditRecordEvent>()
    val uiEvent = channel.receiveAsFlow()

    init {
        savedStateHandle.get<String>("id")?.let { id ->
            if(id != "nul"){
                viewModelScope.launch {
                    recordRepository.getRecordById(id).also {
                        state = EditRecordState(
                            id = it.id,
                            name = it.name,
                            description = it.description,
                            isTask = it.isTask,
                            isChecked = it.isChecked,
                            deadline = if(it.deadline > 0L) LocalDateTimeConverter.longToLocalDateTimeWithTimezone(it.deadline) else null
                        )
                    }
                }
            }
        }
    }

    fun onEvent(event: EditRecordEvent){
        when(event){
            EditRecordEvent.ChangeType -> {
                state = state.copy(isTask = !state.isTask)
            }
            EditRecordEvent.Check -> {
                state = state.copy(isChecked = !state.isChecked)
            }
            EditRecordEvent.DeleteRecord -> {
                viewModelScope.launch {
                    recordRepository.deleteRecord(assembleRecord())
                    channel.send(UiEditRecordEvent.OnBack)
                }
            }
            EditRecordEvent.OnBack -> {
                viewModelScope.launch {
                    if(state.name.isBlank() && state.description.isBlank()){
                        recordRepository.deleteRecord(assembleRecord())
                        channel.send(UiEditRecordEvent.OnBack)
                        return@launch
                    }
                    recordRepository.upsertRecord(assembleRecord())
                    channel.send(UiEditRecordEvent.OnBack)
                }
            }
            is EditRecordEvent.UpdateDesc -> {
                state = state.copy(description = event.value)
            }
            is EditRecordEvent.UpdateName -> {
                state = state.copy(name = event.value)
            }

            EditRecordEvent.CloseTimePickerDialog -> {
                state = state.copy(isDialogOpen = false)
            }
            EditRecordEvent.OpenTimePickerDialog -> {
                state = state.copy(isDialogOpen = true)
            }

            is EditRecordEvent.SaveDeadline -> {
                state = state.copy(
                    deadline = event.date,
                    isDialogOpen = false
                )
            }

            EditRecordEvent.ClearDeadline -> {
                state = state.copy(deadline = null)
            }

            EditRecordEvent.ChangeRecordType -> {
                state = state.copy(isTask = !state.isTask)
                if(!state.isTask){
                    state = state.copy(deadline = null)
                }
            }
        }
    }

    private fun assembleRecord(): RecordEntity {
        return RecordEntity(
            state.id,
            state.isChecked,
            state.name,
            state.description,
            state.isTask,
            if(state.deadline != null) LocalDateTimeConverter.getEpochForUTC(state.deadline!!) else 0L
        )
    }
}