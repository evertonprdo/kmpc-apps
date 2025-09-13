@file:OptIn(ExperimentalTime::class)

package com.prdo.composabletraining

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.KeyEventType
import androidx.compose.ui.input.key.isShiftPressed
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.onPreviewKeyEvent
import androidx.compose.ui.input.key.type
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import kotlinx.datetime.LocalTime
import kotlinx.datetime.format.DateTimeFormat
import kotlinx.datetime.format.Padding
import kotlinx.datetime.format.alternativeParsing
import kotlinx.datetime.format.char
import kotlin.time.ExperimentalTime

/**
 *  kotlinx datetime exercise
 *
 *  Work Hours Tracker
 *
 *  Let the user input two LocalTime values (start and end of work).
 *  Calculate the total number of minutes worked in that day.
 */
@Composable
fun App() {

    var startTime by rememberSaveable { mutableStateOf("") }
    var endTime by rememberSaveable { mutableStateOf("") }

    val dateFormatCaption = @Composable {
        Text("Enter time as: HH:MM", style = MaterialTheme.typography.labelSmall)
    }

    val startLocalTime = LocalTime.parseOrNull(startTime.trim(), formatter)
    val endLocalTime = LocalTime.parseOrNull(endTime.trim(), formatter)

    val isWorkAmountCalculable = startLocalTime != null && endLocalTime != null
    val workedAmount = if (isWorkAmountCalculable)
        calculateWorkedAmountToDisplay(startLocalTime, endLocalTime)
    else
        null

    val focusManager = LocalFocusManager.current

    val startFieldFocusRequester = remember { FocusRequester() }
    val endFieldFocusRequester = remember { FocusRequester() }

    MaterialTheme {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.fillMaxSize()
        ) {
            Column(Modifier.width(IntrinsicSize.Min)) {

                Text(
                    text = "Today's work amount",
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.headlineMedium
                )

                Spacer(Modifier.height(16.dp))

                OutlinedTextField(
                    value = startTime,
                    onValueChange = { startTime = it },
                    label = { Text("start") },
                    isError = startTime.isNotBlank() && startLocalTime == null,
                    supportingText = dateFormatCaption,
                    modifier = Modifier
                        .focusRequester(startFieldFocusRequester)
                        .moveFocusOnTabKeyEvent(focusManager)
                )
                Spacer(Modifier.height(24.dp))
                OutlinedTextField(
                    value = endTime,
                    onValueChange = { endTime = it },
                    label = { Text("end") },
                    isError = endTime.isNotBlank() && endLocalTime == null,
                    supportingText = dateFormatCaption,
                    modifier = Modifier
                        .focusRequester(endFieldFocusRequester)
                        .moveFocusOnTabKeyEvent(focusManager)
                )

                HorizontalDivider(Modifier.padding(vertical = 24.dp))

                if (workedAmount != null)
                    Column(
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = "You've worked",
                            style = MaterialTheme.typography.labelLarge,
                        )
                        Text(
                            text = workedAmount,
                            style = MaterialTheme.typography.titleMedium
                        )
                    }
            }
        }
    }
}

fun calculateWorkedAmountToDisplay(start: LocalTime, end: LocalTime): String {
    val startDaySeconds = start.toSecondOfDay()
    val endDaySeconds = end.toSecondOfDay()

    val minutesWorked = (endDaySeconds - startDaySeconds) / 60

    val hh = minutesWorked / 60
    val mm = minutesWorked % 60

    val hours = if (hh > 0) "${hh}h" else ""
    val minutes = if (mm > 0) "${mm}m" else ""
    val conjunction = if (hh > 0 && mm > 0) " and " else ""

    return "$hours$conjunction$minutes"
}

val formatter: DateTimeFormat<LocalTime> = LocalTime.Format {
    alternativeParsing(
        { hour();char('h');minute() },
        { hour(Padding.NONE);char(':');minute() },
        { hour(Padding.NONE);char('h');minute() },
    ) { hour();char(':');minute() }
}

fun LocalTime.Companion.parseOrNull(time: String, resolver: DateTimeFormat<LocalTime>): LocalTime? =
    try {
        this.parse(time, resolver)
    } catch (_: IllegalArgumentException) {
        null
    } catch (e: Throwable) {
        throw e
    }

fun Modifier.onPreviewTabKeyEvent(onForward: () -> Unit, onBackward: () -> Unit): Modifier =
    this.onPreviewKeyEvent {
        if (it.type == KeyEventType.KeyDown && it.key == Key.Tab) {
            if (it.isShiftPressed)
                onBackward()
            else
                onForward()

            true
        } else {
            false
        }
    }

fun Modifier.moveFocusOnTabKeyEvent(focusManager: FocusManager): Modifier =
    this.onPreviewTabKeyEvent(
        onForward = { focusManager.moveFocus(FocusDirection.Next) },
        onBackward = { focusManager.moveFocus(FocusDirection.Previous) }
    )