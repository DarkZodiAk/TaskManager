package com.example.taskmanager.ui.theme

import androidx.compose.material3.ColorScheme
import androidx.compose.ui.graphics.Color

val Purple80 = Color(0xFFD0BCFF)
val PurpleGrey80 = Color(0xFFCCC2DC)
val Pink80 = Color(0xFFEFB8C8)

val Orange = Color(0xFFFF9041)
val LightOrange = Color(0xFFFFA86A)
val White = Color(0xFFFFFFFF)
val Yellow = Color(0xFFFFA900)
val Gray = Color(0xFFA7A7A7)
val DarkGray = Color(0xFF1D1B20)
val LightRed = Color(0xFFFF6D6D)
val LightGreen = Color(0xFF5CD0C0)
val Light = Color(0xFFFFEDDF)
val Green = Color(0xFF316A41)

val ColorScheme.text : Color
    get() = DarkGray
val ColorScheme.supportingText : Color
    get() = Gray
val ColorScheme.mediumImportance : Color
    get() = LightOrange
val ColorScheme.highImportance : Color
    get() = LightRed
val ColorScheme.lowImportance : Color
    get() = LightGreen

val ColorScheme.Check : Color
    get() = Green