package com.example.taskmanager.auth.presentation.register

import androidx.annotation.StringRes
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.taskmanager.R
import com.example.taskmanager.auth.domain.usecase.RegisterUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val registerUseCases: RegisterUseCases
) : ViewModel() {

    var state by mutableStateOf(RegisterState())
        private set

    private val channel = Channel<UiRegisterEvent>()
    val uiEvent = channel.receiveAsFlow()

    fun onEvent(event: RegisterEvent) {
        when(event){
            is RegisterEvent.NameChanged -> {
                state = state.copy(
                    name = event.value
                )
            }
            is RegisterEvent.EmailChanged -> {
                state = state.copy(
                    email = event.value,
                    isEmailValid = registerUseCases.validateEmailUseCase(event.value),
                    isError = false
                )
            }
            is RegisterEvent.PasswordChanged -> {
                state = state.copy(
                    password = event.value,
                    isError = false
                )
            }
            RegisterEvent.Register -> {
                register()
            }
            RegisterEvent.Login -> {
                viewModelScope.launch {
                    channel.send(UiRegisterEvent.OnLogIn)
                }
            }
        }
    }

    private fun register() {
        if (state.isEmailValid) {
            state = state.copy(isLoading = true)
            viewModelScope.launch {
                registerUseCases.registerUseCase(
                    email = state.email,
                    password = state.password,
                    username = state.name
                ).onSuccess {
                    channel.send(UiRegisterEvent.OnLogIn)
                }.onFailure {
                    state = state.copy(isLoading = false)
                    showError(R.string.network_error_on_login)
                }
            }
        } else {
            state = state.copy(isError = true)
        }
    }

    private fun showError(@StringRes message: Int) {
        viewModelScope.launch {
            channel.send(UiRegisterEvent.ShowErrorMessage(message))
        }
    }
}