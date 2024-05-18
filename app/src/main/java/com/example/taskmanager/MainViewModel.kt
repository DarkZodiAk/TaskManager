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
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val authEventBus: AuthEventBus,
    private val preferences: UserPreferences,
    private val mainUseCases: MainUseCases,
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
                AuthEventBusEvent.Login -> {
                    _state.update { it.copy(isLoggedIn = true) }
                    login()
                }
                AuthEventBusEvent.Logout -> {
                    viewModelScope.launch {
                        mainUseCases.logoutUseCase()
                            .onSuccess { logout() }
                            .onFailure { channel.send(R.string.logout_error) }
                    }
                }
                AuthEventBusEvent.Unauthorized -> logout()
            }
        }.launchIn(viewModelScope)
    }

    private fun logout() {
        viewModelScope.launch {
            _state.update { it.copy(isLoggedIn = false) }
            mainUseCases.clearDatabaseUseCase.invoke()
            preferences.clear()
        }
    }

    private fun validate() {
        viewModelScope.launch {
            mainUseCases.authenticateUseCase()
                .onSuccess {
                    authEventBus.sendEvent(AuthEventBusEvent.Login)
                }
                .onFailure { error ->
                    if(error is HttpException && error.code() == 401){
                        preferences.clear()
                        _state.update { it.copy(isLoggedIn = false, isLoading = false) }
                        channel.send(R.string.token_expired)
                    } else {
                        authEventBus.sendEvent(AuthEventBusEvent.Login)
                    }
                }
        }
    }

    private fun login() {
        mainUseCases.periodicLoadRecordsUseCase()
        mainUseCases.periodicSyncRecordsUseCase()
    }
}