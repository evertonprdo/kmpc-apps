package com.prdo.todolist

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.prdo.todolist.ui.components.ButtonIconPlus
import com.prdo.todolist.ui.components.Input
import com.prdo.todolist.ui.components.TaskList
import com.prdo.todolist.ui.theme.AppTheme
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import todolist.composeapp.generated.resources.Res
import todolist.composeapp.generated.resources.logo

@Composable
@Preview
fun App(viewModel: AppViewModel = viewModel()) {
    val focus = LocalFocusManager.current

    val taskDescription by viewModel.taskDescription.collectAsState()
    val tasks by viewModel.tasks.collectAsState()

    val createdCount = tasks.size
    val checkedCount = tasks.count { it.checked }

    AppTheme {
        val bgColor = MaterialTheme.colorScheme.background
        val bottomBgColor = MaterialTheme.colorScheme.surfaceContainerLowest

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .background(bgColor)
                .clickable(
                    indication = null,
                    interactionSource = remember { MutableInteractionSource() }
                ) { focus.clearFocus() }
                .fillMaxSize()
        ) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .background(bottomBgColor)
                    .height(175.dp)
                    .fillMaxWidth()
            ) {
                Image(
                    painterResource(Res.drawable.logo),
                    null
                )
            }

            Column(Modifier.sizeIn(maxWidth = 864.dp)) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(4.dp),
                    modifier = Modifier
                        .offset(y = (-24).dp)
                        .padding(horizontal = 24.dp)
                ) {
                    Input(
                        value = taskDescription,
                        onValueChange = viewModel::onTaskDescriptionUpdate,
                        placeholder = "Add a new task",
                        modifier = Modifier.weight(1f)
                    )

                    ButtonIconPlus(
                        onClick = {
                            focus.clearFocus()
                            viewModel.onNewTask()
                        }
                    )
                }

                TaskList(
                    tasks = tasks,
                    createdCount = createdCount,
                    checkedCount = checkedCount,
                    onToggleTask = viewModel::onToggleTask,
                    onRemoveTask = viewModel::onRemoveTask
                )
            }
        }
    }
}