package com.example.taskmanager.auth.presentation.login

import androidx.annotation.StringRes
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.taskmanager.R
import com.example.taskmanager.auth.domain.usecase.LoginUseCases
import com.example.taskmanager.core.domain.eventBus.AuthEventBus
import com.example.taskmanager.core.domain.eventBus.AuthEventBusEvent
import com.example.taskmanager.core.domain.local.UserPreferences
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUseCases: LoginUseCases,
    private val preferences: UserPreferences,
    private val authEventBus: AuthEventBus
) : ViewModel() {
    
    var state by mutableStateOf(LoginState())
        private set
    
    private val channel = Channel<UiLoginEvent>()
    val uiEvent = channel.receiveAsFlow()

    fun onEvent(event: LoginEvent) {
        when(event){
            is LoginEvent.EmailChanged -> {
                state = state.copy(
                    email = event.value,
                    isEmailValid = loginUseCases.validateEmailUseCase(event.value),
                    isError = false
                )
            }
            is LoginEvent.PasswordChanged -> {
                state = state.copy(
                    password = event.value,
                    isError = false
                )
            }
            LoginEvent.Login -> {
                login()
            }
            LoginEvent.SignUp -> {
                viewModelScope.launch {
                    channel.send(UiLoginEvent.OnSignUp)
                }
            }
        }
    }

    private fun login() {
        if (state.isEmailValid) {
            val email = state.email
            state = state.copy(isLoading = true)
            viewModelScope.launch {
                loginUseCases.loginUseCase(
                    email = state.email,
                    password = state.password
                ).onSuccess { user ->
                    preferences.saveUser(user.copy(email = email))
                    authEventBus.sendEvent(AuthEventBusEvent.Login)
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
            channel.send(UiLoginEvent.ShowErrorMessage(message))
        }
    }
}