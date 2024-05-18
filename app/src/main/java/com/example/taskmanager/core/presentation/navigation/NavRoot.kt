package com.example.taskmanager.core.presentation.navigation

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.taskmanager.auth.presentation.login.LoginScreen
import com.example.taskmanager.auth.presentation.register.RegisterScreen
import com.example.taskmanager.records.presentation.calendar.CalendarScreen
import com.example.taskmanager.records.presentation.editRecord.EditRecordScreen
import com.example.taskmanager.records.presentation.records.RecordsScreen
import com.example.taskmanager.records.presentation.settings.SettingsScreen

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
            LoginScreen(onSignUp = { navController.navigate(Screens.RegisterScreen.route) })
        }
        composable(Screens.RegisterScreen.route) {
            RegisterScreen(onLogIn = { navController.navigate(Screens.LoginScreen.route) })
        }
        composable(Screens.RecordsScreen.route) {
            RecordsScreen(
                navigate = { navController.navigate(it) },
                onOpenRecord = {
                    Log.d("Navigate", Screens.EditRecordScreen.route + "?id=$it")
                    navController.navigate(Screens.EditRecordScreen.route + "?id=$it")
                }
            )
        }
        composable(Screens.CalendarScreen.route) {
            CalendarScreen(
                navigate = { navController.navigate(it) },
                onOpenRecord = {
                    navController.navigate(Screens.EditRecordScreen.route + "?id=$it")
                }
            )
        }
        composable(Screens.SettingsScreen.route) {
            SettingsScreen(navigate = { navController.navigate(it) })
        }
        composable(
            route = Screens.EditRecordScreen.route + "?id={id}",
            arguments = listOf(
                navArgument("id") {
                    type = NavType.StringType
                }
            )
        ) {
            EditRecordScreen(onBack = { navController.navigateUp() })
        }
    }
}