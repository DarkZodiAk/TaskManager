package com.example.taskmanager.records.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Description
import androidx.compose.material3.Card
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.example.taskmanager.records.data.util.string
import com.example.taskmanager.ui.theme.body16
import java.time.LocalDateTime

@Composable
fun RecordCard(
    title: String,
    isChecked: Boolean,
    isTask: Boolean,
    deadline: LocalDateTime?,
    onCheck: (Boolean) -> Unit,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .clip(RoundedCornerShape(16.dp)),
        onClick = onClick
    ) {
        Column(
            modifier = Modifier
                .background(MaterialTheme.colorScheme.surface)
                .fillMaxSize()
                .padding(vertical = 8.dp)
        ) {
            Row(
                modifier = Modifier
                    .background(MaterialTheme.colorScheme.surface)
                    .fillMaxSize(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                if(isTask){
                    Checkbox(
                        checked = isChecked,
                        onCheckedChange = onCheck
                    )
                } else {
                    Icon(
                        imageVector = Icons.Outlined.Description,
                        contentDescription = "note",
                        modifier = Modifier.padding(12.dp)
                    )
                }
                
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = title,
                    style = MaterialTheme.typography.bodyLarge,
                )
            }
            deadline?.let {
                Text(
                    text = deadline.string(),
                    style = MaterialTheme.typography.body16,
                    modifier = Modifier.padding(start = 58.dp)
                )
            }
        }
    }
}