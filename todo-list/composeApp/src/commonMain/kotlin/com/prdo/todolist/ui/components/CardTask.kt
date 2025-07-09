package com.prdo.todolist.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import com.prdo.todolist.model.Task

@Composable
fun CardTask(
    task: Task,
    onCheckedChange: (Long) -> Unit,
    onRemove: (Long) -> Unit
) {
    val colors = MaterialTheme.colorScheme

    val bgColor = colors.surfaceDim
    val borderStroke = BorderStroke(
        width = 1.dp,
        color = colors.surface
    )

    val shape = RoundedCornerShape(8.dp)
    val textStyle = MaterialTheme.typography.bodyMedium.copy(
        color = if (task.checked) colors.surfaceBright else colors.surfaceContainerHighest,
        textDecoration = if (task.checked) TextDecoration.LineThrough else null
    )

    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier
            .background(bgColor, shape)
            .border(borderStroke, shape)
            .padding(12.dp, 12.dp, 8.dp, 12.dp)
    ) {
        Checkbox(
            value = task.checked,
            onValueChange = { onCheckedChange(task.id) }
        )

        Text(
            text = task.description,
            style = textStyle,
            modifier = Modifier.weight(1f)
        )

        ButtonIconTrash(onClick = { onRemove(task.id) })
    }
}