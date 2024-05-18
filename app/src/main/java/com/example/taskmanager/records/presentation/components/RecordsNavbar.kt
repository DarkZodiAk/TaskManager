package com.example.taskmanager.records.presentation.components

import androidx.compose.foundation.layout.Box
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import com.example.taskmanager.R
import com.example.taskmanager.ui.theme.DarkGray

data class BottomNavigationItem(
    val title: String,
    val destination: String,
    val icon: ImageVector
)

@Composable
fun RecordsNavbar(
    initialIndex: Int,
    onClick: (String) -> Unit
) {
    val items = listOf(
        BottomNavigationItem(
            title = stringResource(id = R.string.records),
            destination = "records",
            icon = Icons.Filled.Home
        ),
        BottomNavigationItem(
            title = stringResource(id = R.string.calendar),
            destination = "calendar",
            icon = Icons.Filled.CalendarToday
        ),
        BottomNavigationItem(
            title = stringResource(id = R.string.settings),
            destination = "settings",
            icon = Icons.Filled.Settings
        )
    )

    var selectedItemIndex by remember {
        mutableStateOf(initialIndex)
    }

    NavigationBar(
        containerColor = MaterialTheme.colorScheme.background
    ) {
        items.forEachIndexed { index, item ->
            NavigationBarItem(
                selected = selectedItemIndex == index,
                onClick = {
                    selectedItemIndex = index
                    onClick(item.destination)
                },
                label = {
                    Text(text = item.title)
                },
                icon = { 
                    Box{
                        Icon(
                            imageVector = item.icon,
                            contentDescription = item.title,
                            tint = DarkGray
                        )
                    }
                }
            )
        }
    }
}