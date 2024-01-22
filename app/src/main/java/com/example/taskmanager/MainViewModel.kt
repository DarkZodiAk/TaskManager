package com.example.taskmanager

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.taskmanager.core.domain.eventBus.AuthEventBus
import com.example.taskmanager.core.domain.eventBus.AuthEventBusEvent
import com.example.taskmanager.core.domain.local.UserPreferences
import com.example.taskmanager.core.domain.usecase.MainUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException
import java.net.ConnectException
import java.net.UnknownHostException
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val authEventBus: AuthEventBus,
    private val preferences: UserPreferences,
    private val mainUseCases: MainUseCases
) : ViewModel() {

    private val _state = MutableStateFlow(
        MainState(isLoggedIn = preferences.isLoggedIn())
    )
    val state = _state.asStateFlow()

    private val channel = Channel<Int>()
    val uiEvent = channel.receiveAsFlow()

    init {
        if(_state.value.isLoggedIn){
            validate()
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

    private fun logout() {
        _state.update { it.copy(isLoggedIn = false) }
        preferences.clear()
    }

    private fun validate() {
        viewModelScope.launch {
            mainUseCases.authenticateUseCase()
                .onSuccess {

                }
                .onFailure { error ->
                    if(error is HttpException && error.code() == 401){
                        preferences.clear()
                        _state.update { it.copy(isLoggedIn = false, isLoading = false) }
                        channel.send(R.string.token_expired)
                    }

                }
        }
    }
}