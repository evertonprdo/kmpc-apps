package com.prdo.todolist.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.prdo.todolist.model.Task

@Composable
fun TaskList(
    tasks: List<Task>,
    createdCount: Int,
    checkedCount: Int,
    onToggleTask: (Long) -> Unit,
    onRemoveTask: (Long) -> Unit
) {
    Column {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp)
        ) {
            CounterLabel(
                title = "Created",
                count = createdCount,
                color = MaterialTheme.colorScheme.primary
            )

            CounterLabel(
                title = "Completed",
                count = checkedCount,
                color = MaterialTheme.colorScheme.secondary
            )
        }

        HorizontalDivider(
            modifier = Modifier.padding(vertical = 20.dp, horizontal = 24.dp),
            thickness = 1.dp,
            color = MaterialTheme.colorScheme.surface
        )

        if (tasks.isNotEmpty())
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(8.dp),
                contentPadding = PaddingValues(start = 24.dp, end = 24.dp, bottom = 64.dp)
            ) {
                items(tasks) {
                    CardTask(
                        task = it,
                        onCheckedChange = onToggleTask,
                        onRemove = onRemoveTask
                    )
                }
            }
        else
            EmptyWarning()
    }
}

@Composable
fun CounterLabel(
    title: String,
    count: Int,
    color: Color
) {
    val counterTextStyle = MaterialTheme.typography.titleMedium.copy(
        color = MaterialTheme.colorScheme.surfaceContainerHigh,
    )
    val textStyle = MaterialTheme.typography.titleMedium.copy(color = color)
    val shape = CircleShape

    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(text = title, style = textStyle)
        Text(
            text = count.toString(),
            style = counterTextStyle,
            modifier = Modifier
                .background(color = MaterialTheme.colorScheme.surface, shape = shape)
                .padding(8.dp, 2.dp)
        )
    }
}