package com.example.taskmanager.auth.presentation.components

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.example.taskmanager.R

@Composable
fun PasswordTextField(
    value: String,
    onValueChange: (String) -> Unit,
    isError: Boolean,
    @StringRes label: Int,
    modifier: Modifier = Modifier
) {
    var isPasswordVisible by remember { mutableStateOf(false) }

    TextField(
        value = value,
        onValueChange = { onValueChange(it) },
        textStyle = MaterialTheme.typography.bodyLarge,
        visualTransformation = if (isPasswordVisible) {
            VisualTransformation.None
        } else {
            PasswordVisualTransformation()
        },
        trailingIcon = {
            if (!isPasswordVisible) {
                IconButton(onClick = { isPasswordVisible = true } ) {
                    Icon(
                        imageVector = Icons.Default.Visibility,
                        tint = Color.Black,
                        contentDescription = stringResource(id = R.string.visibility)
                    )
                }
            } else {
                IconButton(onClick = { isPasswordVisible = false }) {
                    Icon(
                        imageVector = Icons.Filled.VisibilityOff,
                        tint = Color.Black,
                        contentDescription = stringResource(id = R.string.visibility_off)
                    )
                }
            }
        },
        label = { Text(text = stringResource(id = label))},
        isError = isError,
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
    )
}