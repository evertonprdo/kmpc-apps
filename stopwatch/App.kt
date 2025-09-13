@file:OptIn(ExperimentalTime::class)

package com.prdo.composabletraining

import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlinx.coroutines.CoroutineStart
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import kotlin.time.Clock
import kotlin.time.Duration
import kotlin.time.ExperimentalTime
import kotlin.time.Instant

/**
 * kotlinx datetime and coroutines exercise
 *
 * Stopwatch
 *  Implement a stopwatch that can start(), pause(), and reset(),
 * using Instant differences to measure elapsed time.
 */
@Composable
fun App(viewModel: AppViewModel = viewModel()) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    val elapsedTime = uiState.elapsedTime
    val clockState = uiState.clockState

    val callback by rememberUpdatedState(
        newValue = when (clockState) {
            ClockState.ZERO -> viewModel::start
            ClockState.PAUSED -> viewModel::start
            ClockState.RUNNING -> viewModel::pause
        }
    )

    MaterialTheme {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.fillMaxSize()
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = elapsedTime,
                    style = MaterialTheme.typography.displayMedium,
                    modifier = Modifier
                        .pointerInput(Unit) {
                            detectTapGestures(
                                onLongPress = { viewModel.reset() },
                                onTap = { callback() }
                            )
                        }
                )
                Spacer(Modifier.height(48.dp))
                Text(
                    text = "Tap for Start/Pause\nLong Press for Reset",
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.labelLarge
                )
            }
        }
    }
}

class AppViewModel(
    private val clock: Clock = Clock.System
) : ViewModel() {

    private lateinit var clockJob: Job
    private var startTime: Instant = Instant.DISTANT_PAST
    private var accumulatedElapsedTime = Duration.ZERO

    private var elapsedTime = MutableStateFlow(Duration.ZERO)
    private var clockState = MutableStateFlow(ClockState.ZERO)

    val uiState = combine(elapsedTime, clockState) {
        UiState(
            elapsedTime = elapsedTime.value.toDisplay(),
            clockState = clockState.value
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = UiState()
    )

    private fun prepareClock() {
        clockJob = viewModelScope.launch(start = CoroutineStart.LAZY) {

            while (isActive) {
                val currentElapsedTime = clock.now() - startTime
                elapsedTime.value = accumulatedElapsedTime + currentElapsedTime

                delay(16)
            }
        }
    }

    fun pause() {
        if (clockState.value != ClockState.RUNNING)
            return

        clockState.value = ClockState.PAUSED
        clockJob.cancel()

        accumulatedElapsedTime += clock.now() - startTime
    }

    fun start() {
        if (clockState.value == ClockState.RUNNING)
            return

        prepareClock()
        startTime = clock.now()

        clockJob.start()
        clockState.value = ClockState.RUNNING
    }

    fun reset() {
        if (clockState.value == ClockState.ZERO)
            return

        if (clockState.value == ClockState.RUNNING)
            pause()

        accumulatedElapsedTime = Duration.ZERO
        elapsedTime.value = Duration.ZERO
        clockState.value = ClockState.ZERO
    }

    override fun onCleared() {
        super.onCleared()
        if (::clockJob.isInitialized && clockJob.isActive)
            clockJob.cancel()
    }
}

data class UiState(
    val elapsedTime: String = "",
    val clockState: ClockState = ClockState.ZERO
)

enum class ClockState {
    PAUSED,
    RUNNING,
    ZERO
}

fun Duration.toDisplay(): String =
    this.toComponents { hours, minutes, seconds, nanoseconds ->
        val hh = hours.toString().padStart(2, '0')
        val mm = minutes.toString().padStart(2, '0')
        val ss = seconds.toString().padStart(2, '0')
        val ns = nanoseconds.toString().take(3).padStart(3, '0')

        "$hh:$mm:$ss.$ns"
    }