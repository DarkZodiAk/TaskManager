package com.example.taskmanager.records.presentation.settings

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.taskmanager.core.domain.eventBus.AuthEventBus
import com.example.taskmanager.core.domain.eventBus.AuthEventBusEvent
import com.example.taskmanager.core.domain.local.UserPreferences
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val preferences: UserPreferences,
    private val authEventBus: AuthEventBus
) : ViewModel() {
    var name by mutableStateOf("")
        private set
    var email by mutableStateOf("")
        private set

    init {
        preferences.getUser()?.let {
            name = it.username
            email = it.email
        }
    }

    fun logout() {
        viewModelScope.launch {
            authEventBus.sendEvent(AuthEventBusEvent.Logout)
        }
    }
}