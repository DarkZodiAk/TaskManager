package com.example.taskmanager.core.presentation.components

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun LoadingTextButton(
    @StringRes textRes: Int,
    isLoading: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Button(
        onClick = { onClick() },
        modifier = modifier,
        shape = RoundedCornerShape(38.dp),
        contentPadding = PaddingValues(
            top = 12.dp,
            bottom = 12.dp,
            start = 16.dp,
            end = 16.dp
        )
    ) {
        ProgressBarText(
            isLoading = isLoading,
            textRes = textRes,
            textStyle = MaterialTheme.typography.bodyMedium
        )
    }
}