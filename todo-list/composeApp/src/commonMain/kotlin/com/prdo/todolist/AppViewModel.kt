package com.prdo.todolist

import androidx.lifecycle.ViewModel
import com.prdo.todolist.model.Task
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class AppViewModel : ViewModel() {
    private val _taskDescription = MutableStateFlow("")
    val taskDescription: StateFlow<String> = _taskDescription.asStateFlow()

    private val _tasks = MutableStateFlow<List<Task>>(emptyList())
    val tasks: StateFlow<List<Task>> = _tasks.asStateFlow()

    fun onTaskDescriptionUpdate(newDescription: String) {
        _taskDescription.value = newDescription
    }

    fun onNewTask() {
        if (_taskDescription.value.isNotEmpty()) {
            val newTask = Task.new(description = taskDescription.value)
            _tasks.update {
                it + newTask
            }
            _taskDescription.value = ""
        }
    }

    fun onToggleTask(id: Long) {
        _tasks.update {
            it.map { task ->
                if (task.id == id) {
                    task.copy(checked = !task.checked)
                } else {
                    task
                }
            }
        }
    }

    fun onRemoveTask(id: Long) {
        _tasks.update {
            it.filterNot { task -> task.id == id }
        }
    }
}