package com.example.taskmanager.records.presentation.components

import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.LocalTextStyle
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardCapitalization

@Composable
fun BasicTextFieldWithPlaceholder(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    textStyle: TextStyle = LocalTextStyle.current,
    placeholder: @Composable (() -> Unit)? = null
) {
    BasicTextField(
        value = value,
        onValueChange = onValueChange,
        modifier = modifier,
        textStyle = textStyle,
        enabled = true,
        keyboardOptions = KeyboardOptions.Default.copy(
            capitalization = KeyboardCapitalization.Sentences,
        ),
        decorationBox = { innerTextField ->
            if (value.isBlank()) {
                placeholder?.let { it() }
            }
            innerTextField()
        }
    )
}