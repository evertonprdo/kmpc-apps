package com.prdo.todolist

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.unit.dp
import com.prdo.todolist.ui.components.ButtonIconPlus
import com.prdo.todolist.ui.components.ButtonIconTrash
import com.prdo.todolist.ui.components.Input
import com.prdo.todolist.ui.theme.AppTheme
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun App() {
    var text by remember { mutableStateOf("") }
    val focus = LocalFocusManager.current

    AppTheme {
        val bgColor = MaterialTheme.colorScheme.background

        Column(
            modifier = Modifier.background(bgColor).padding(top = 64.dp).fillMaxSize()
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Input(
                    value = text,
                    onValueChange = { text = it },
                    placeholder = "Add a new task",
                    modifier = Modifier.weight(1f)
                )

                ButtonIconPlus(
                    onClick = { focus.clearFocus() }
                )
            }

            ButtonIconTrash({})
        }
    }
}