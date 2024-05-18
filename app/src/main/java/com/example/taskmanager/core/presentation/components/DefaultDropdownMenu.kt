package com.example.taskmanager.core.presentation.components

import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.example.taskmanager.ui.theme.body16

@Composable
fun DefaultDropDownMenu(
    actions: HashMap<String, () -> Unit>,
    onDismissRequest: () -> Unit
) {
    DropdownMenu(
        expanded = true,
        onDismissRequest = {
            onDismissRequest()
        },
    ) {
        actions.forEach { action ->
            DropdownMenuItem(
                onClick = {
                    action.value()
                    onDismissRequest()
                },
                text = {
                    Text(
                        text = action.key,
                        style = MaterialTheme.typography.body16,
                    )
                }
            )
        }
    }
}