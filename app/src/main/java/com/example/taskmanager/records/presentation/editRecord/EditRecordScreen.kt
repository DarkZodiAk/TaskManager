package com.example.taskmanager.records.presentation.editRecord

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.taskmanager.R
import com.example.taskmanager.core.presentation.components.DefaultDropDownMenu
import com.example.taskmanager.records.presentation.string
import com.example.taskmanager.records.presentation.components.BasicTextFieldWithPlaceholder
import com.example.taskmanager.records.presentation.editRecord.components.DateTimePickerDialog

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditRecordScreen(
    viewModel: EditRecordViewModel = hiltViewModel(),
    onBack: () -> Unit
) {
    val state = viewModel.state

    var dropdownIsVisible by remember { mutableStateOf(false) }

    LaunchedEffect(true) {
        viewModel.uiEvent.collect { event ->
            when(event){
                UiEditRecordEvent.OnBack -> onBack()
            }
        }
    }

    DateTimePickerDialog(
        isDialogOpen = state.isDialogOpen,
        onDismiss = { viewModel.onEvent(EditRecordEvent.CloseTimePickerDialog) },
        onDateSelected = { viewModel.onEvent(EditRecordEvent.SaveDeadline(it)) }
    )

    
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    if(state.isTask) {
                        Row {
                            Text(
                                text = state.deadline?.string() ?: stringResource(id = R.string.choose_date),
                                color = MaterialTheme.colorScheme.secondary,
                                modifier = Modifier.clickable {
                                    viewModel.onEvent(EditRecordEvent.OpenTimePickerDialog)
                                }
                            )
                            state.deadline?.let {
                                Spacer(modifier = Modifier.width(8.dp))
                                Icon(
                                    imageVector = Icons.Default.Close,
                                    contentDescription = stringResource(id = R.string.clear_date),
                                    tint = MaterialTheme.colorScheme.secondary,
                                    modifier = Modifier.clickable {
                                        viewModel.onEvent(EditRecordEvent.ClearDeadline)
                                    }
                                )
                            }
                        }
                    }
                },
                navigationIcon = {
                    IconButton(onClick = { viewModel.onEvent(EditRecordEvent.OnBack) }) {
                        Icon(imageVector = Icons.Default.ArrowBack, stringResource(id = R.string.back))
                    }
                },
                actions = {
                    IconButton(onClick = { dropdownIsVisible = true }) {
                        Icon(
                            imageVector = Icons.Default.MoreVert,
                            contentDescription = stringResource(id = R.string.other_options)
                        )
                    }
                    if(dropdownIsVisible) {
                        DefaultDropDownMenu(
                            actions = getDropdownActions(
                                id = state.id,
                                isTask = state.isTask,
                                onChangeType = { viewModel.onEvent(EditRecordEvent.ChangeRecordType) },
                                onDelete = { viewModel.onEvent(EditRecordEvent.DeleteRecord) }
                            ),
                            onDismissRequest = { dropdownIsVisible = false }
                        )
                    }
                }
            )
        },
        containerColor = MaterialTheme.colorScheme.surface
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(horizontal = 16.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .padding(vertical = if(viewModel.state.isTask) 0.dp else 16.dp)
            ) {
                if(viewModel.state.isTask){
                    Checkbox(
                        checked = viewModel.state.isChecked,
                        onCheckedChange = { viewModel.onEvent(EditRecordEvent.Check) }
                    )
                }
                Spacer(modifier = Modifier.width(8.dp))
                BasicTextFieldWithPlaceholder(
                    value = state.name,
                    onValueChange = { viewModel.onEvent(EditRecordEvent.UpdateName(it)) },
                    textStyle = TextStyle(
                        fontWeight = FontWeight.ExtraBold,
                        fontSize = 24.sp
                    ),
                    placeholder = {
                        Text(
                            text = stringResource(id = R.string.title),
                            color = Color.Gray,
                            fontSize = 24.sp,
                            modifier = Modifier
                                .padding(start = 4.dp),
                            fontWeight = FontWeight.ExtraBold
                        )
                    }
                )
            }

            Spacer(modifier = Modifier.height(8.dp))
            BasicTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .padding(horizontal = 8.dp),
                value = state.description,
                textStyle = TextStyle(fontSize = 24.sp),
                onValueChange = { viewModel.onEvent(EditRecordEvent.UpdateDesc(it)) },
                keyboardOptions = KeyboardOptions.Default.copy(
                    capitalization = KeyboardCapitalization.Sentences,
                )
            )
        }
    }

    BackHandler {
        viewModel.onEvent(EditRecordEvent.OnBack)
    }
}

@Composable
fun getDropdownActions(
    id: String,
    isTask: Boolean,
    onChangeType: () -> Unit,
    onDelete: () -> Unit
): HashMap<String, () -> Unit> {
    val result = hashMapOf(
        (if(isTask) stringResource(id = R.string.convert_to_note) else stringResource(id = R.string.convert_to_task))
                to onChangeType
    )
    if(id.isNotBlank()) {
        result.put(stringResource(id = R.string.delete), onDelete)
    }
    return result
}