package com.example.taskmanager.core.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

@Composable
fun NavRoot(
    navController: NavHostController,
    isLoggedIn: Boolean,
    modifier: Modifier
) {
    NavHost(
        navController = navController,
        startDestination = if(isLoggedIn) Screens.RecordsScreen.route else Screens.LoginScreen.route,
        modifier = modifier
    ) {
        composable(Screens.LoginScreen.route) {

        }
        composable(Screens.RegisterScreen.route) {

        }
        composable(Screens.RecordsScreen.route) {

        }
    }
}