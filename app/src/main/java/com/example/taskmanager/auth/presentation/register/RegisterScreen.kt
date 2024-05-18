package com.example.taskmanager.auth.presentation.register

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.taskmanager.R
import com.example.taskmanager.auth.presentation.components.PasswordTextField
import com.example.taskmanager.auth.presentation.components.VerifiableTextField
import com.example.taskmanager.core.presentation.components.LoadingTextButton
import com.example.taskmanager.core.presentation.components.LocalSnackbarHostState
import kotlinx.coroutines.launch

@Composable
fun RegisterScreen(
    viewModel: RegisterViewModel = hiltViewModel(),
    onLogIn: () -> Unit
) {
    val scope = rememberCoroutineScope()
    val context = LocalContext.current
    val snackbarState = LocalSnackbarHostState.current

    LaunchedEffect(key1 = true) {
        viewModel.uiEvent.collect { event ->
            when(event){
                UiRegisterEvent.OnLogIn -> onLogIn()
                is UiRegisterEvent.ShowErrorMessage -> {
                    scope.launch {
                        snackbarState.showSnackbar(context.getString(event.error))
                    }
                }
            }
        }
    }

    val state = viewModel.state

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = stringResource(id = R.string.registration_header),
            style = MaterialTheme.typography.titleLarge
        )
        Spacer(modifier = Modifier.height(24.dp))
        VerifiableTextField(
            value = state.name,
            onValueChange = { viewModel.onEvent(RegisterEvent.NameChanged(it)) },
            label = state.nameLabel,
            isError = false,
            isValid = false
        )
        Spacer(modifier = Modifier.height(24.dp))
        VerifiableTextField(
            value = state.email,
            onValueChange = { viewModel.onEvent(RegisterEvent.EmailChanged(it)) },
            label = state.emailLabel,
            isError = state.isError,
            isValid = state.isEmailValid
        )
        Spacer(modifier = Modifier.height(24.dp))
        PasswordTextField(
            value = state.password,
            onValueChange = { viewModel.onEvent(RegisterEvent.PasswordChanged(it)) },
            isError = state.isError,
            label = state.passwordLabel
        )
        Spacer(modifier = Modifier.height(24.dp))
        LoadingTextButton(
            textRes = R.string.to_register,
            isLoading = state.isLoading,
            onClick = { viewModel.onEvent(RegisterEvent.Register) }
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = stringResource(id = R.string.to_login),
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.clickable {
                viewModel.onEvent(RegisterEvent.Login)
            }
        )
    }
}