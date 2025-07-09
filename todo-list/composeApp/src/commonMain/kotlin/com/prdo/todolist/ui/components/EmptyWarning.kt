package com.prdo.todolist.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.painterResource
import todolist.composeapp.generated.resources.Res
import todolist.composeapp.generated.resources.clipboard

@Composable
fun EmptyWarning() {
    val color = MaterialTheme.colorScheme.surfaceBright

    val boldStyle = MaterialTheme.typography.titleMedium.copy(color = color)
    val regularStyle = MaterialTheme.typography.bodyMedium.copy(color = color)

    Column(
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxWidth().padding(20.dp, 48.dp)
    ) {
        Image(
            painter = painterResource(Res.drawable.clipboard),
            contentDescription = null,
            modifier = Modifier.size(56.dp)
        )

        Column {
            Text(text = "You don't have any registered tasks yet", style = boldStyle)
            Text(text = "Create tasks and organize your to-do items", style = regularStyle)
        }
    }
}