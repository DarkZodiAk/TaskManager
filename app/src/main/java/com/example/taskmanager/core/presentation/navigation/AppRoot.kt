package com.example.taskmanager.core.presentation.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.taskmanager.MainViewModel
import com.example.taskmanager.core.presentation.components.LocalSnackbarHostState

@Composable
fun AppRoot(
    navController: NavHostController
) {

    val viewModel: MainViewModel = hiltViewModel()
    val context = LocalContext.current
    val localSnackbarHostState = LocalSnackbarHostState.current

    CompositionLocalProvider(
        LocalSnackbarHostState provides localSnackbarHostState
    ) {
        LaunchedEffect(true){
            viewModel.uiEvent.collect { message ->
                localSnackbarHostState.showSnackbar(context.getString(message))
            }
        }

        Scaffold(
            snackbarHost = { SnackbarHost(localSnackbarHostState) }
        ) { paddingValues ->
            NavRoot(
                navController = navController,
                isLoggedIn = viewModel.state.collectAsState().value.isLoggedIn,
                modifier = Modifier.padding(paddingValues)
            )
        }
    }
}