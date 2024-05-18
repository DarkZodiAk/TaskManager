package com.example.taskmanager.core.presentation.components

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp

@Composable
fun ProgressBarText(
    isLoading: Boolean,
    @StringRes textRes: Int,
    textStyle: TextStyle,
    modifier: Modifier = Modifier
) {
    val text = stringResource(id = textRes)

    if (isLoading) {
        CircularProgressIndicator(
            color = Color.White,
            modifier = modifier
                .width(20.dp)
                .height(20.dp),
            strokeWidth = 1.dp,
        )
    } else {
        Text(
            text = text,
            style = textStyle,
            modifier = modifier
        )
    }
}