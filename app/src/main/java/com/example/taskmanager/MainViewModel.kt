package com.example.taskmanager

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.taskmanager.core.domain.eventBus.AuthEventBus
import com.example.taskmanager.core.domain.eventBus.AuthEventBusEvent
import com.example.taskmanager.core.domain.local.UserPreferences
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val authEventBus: AuthEventBus,
    private val preferences: UserPreferences,

) : ViewModel() {

    private val _state = MutableStateFlow(MainState())
    val state = _state.asStateFlow()

    private val channel = Channel<Int>()
    val uiEvent = channel.receiveAsFlow()

    init {
        if(_state.value.isLoggedIn){
            TODO("Execute validation")
        } else {
            _state.update { it.copy(isLoading = false) }
        }

        authEventBus.authBus.onEach { event ->
            when(event){
                AuthEventBusEvent.LogIn -> {
                    _state.update { it.copy(isLoggedIn = true) }
                }
                AuthEventBusEvent.LogOut -> logout()
                AuthEventBusEvent.Unauthorized -> logout()
            }
        }.launchIn(viewModelScope)
    }

    private fun logout() {}
}