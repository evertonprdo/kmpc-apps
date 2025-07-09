package com.prdo.todolist.model

import kotlin.time.Clock
import kotlin.time.ExperimentalTime

data class Task(
    val id: Long,
    val description: String,
    val checked: Boolean,
) {
    companion object {
        @OptIn(ExperimentalTime::class)
        fun new(description: String): Task {
            val id = Clock.System.now().epochSeconds

            return Task(
                id = id,
                description = description,
                checked = false
            )
        }
    }
}