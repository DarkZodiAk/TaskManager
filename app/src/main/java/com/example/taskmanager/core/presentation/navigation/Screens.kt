package com.example.taskmanager.core.presentation.navigation

sealed class Screens(val route: String) {
    object LoginScreen: Screens("login")
    object RegisterScreen: Screens("register")
    object RecordsScreen: Screens("records")
    object EditRecordScreen: Screens("edit_record")
    object CalendarScreen: Screens("calendar")
    object SettingsScreen: Screens("settings")
}