@file:OptIn(ExperimentalTime::class)

package com.prdo.composabletraining

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.asClock
import kotlinx.datetime.atTime
import kotlinx.datetime.format
import kotlinx.datetime.format.DateTimeFormat
import kotlinx.datetime.format.char
import kotlinx.datetime.isoDayNumber
import kotlinx.datetime.plus
import kotlinx.datetime.toInstant
import kotlinx.datetime.toLocalDateTime
import kotlin.time.Clock
import kotlin.time.Duration.Companion.milliseconds
import kotlin.time.ExperimentalTime
import kotlin.time.Instant
import kotlin.time.TestTimeSource

/**
 *  Week Progress Indicator
 *
 *  Show how far the current week has progressed as a percentage,
 *  based on the current LocalDateTime.
 */
@Composable
fun App() {
    val testTimeSource = remember { TestTimeSource() }
    val clock = remember { testTimeSource.createTestClock() }

    var now: Instant by remember { mutableStateOf(clock.now()) }
    val percentUntilMonday = percentUntilNextMonday(now)

    val nextMondayProgress = (percentUntilMonday * 100).formatToFixedDecimal() + '%'
    val todayFormatted = now.toLocalDateTime(timeZone).format(LocalDateTime.DateTimeFormater)

    val tick = 16L
    val step = 128

    val minMs = tick.toFloat() // RealTime
    val maxMs = (1000f * 60 * 60 * 3) / tick // 3h per tick

    var updateIntervalMs by remember { mutableStateOf(minMs) }

    LaunchedEffect(updateIntervalMs) {
        while (percentUntilMonday < 1) {
            testTimeSource += (updateIntervalMs.toLong()).milliseconds
            now = clock.now()

            delay(tick)
        }
    }

    MaterialTheme {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.fillMaxSize()
        ) {
            Column(
                verticalArrangement = Arrangement.spacedBy(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.widthIn(max = 1440.dp).padding(horizontal = 24.dp)
            ) {
                LinearProgressIndicator(
                    progress = { percentUntilMonday },
                    modifier = Modifier.fillMaxWidth()
                )

                Column(
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    Text(
                        text = "End of the week progress: $nextMondayProgress",
                        style = MaterialTheme.typography.titleMedium
                    )
                    Text("Now: $todayFormatted", style = MaterialTheme.typography.titleLarge)
                }

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                ) {
                    Text("Real Time", style = MaterialTheme.typography.labelSmall)
                    Slider(
                        value = updateIntervalMs,
                        onValueChange = { updateIntervalMs = it },
                        valueRange = minMs..maxMs,
                        steps = step,
                        modifier = Modifier.weight(1f)
                    )
                    Text("3h/Tick", style = MaterialTheme.typography.labelSmall)
                }
            }
        }
    }
}

fun percentUntilNextMonday(now: Instant): Float {
    val localDateTime = now.toLocalDateTime(TimeZone.currentSystemDefault())
    val daysAfterSunday = localDateTime.dayOfWeek.isoDayNumber % 7
    val daysUntilSunday = 7 - daysAfterSunday

    val nextMonday =
        localDateTime.date
            .plus(daysUntilSunday, DateTimeUnit.DAY)
            .atTime(0, 0)
            .toInstant(timeZone)

    val timeUntilNextMonday = nextMonday - now
    val secondsInAWeek = 60 * 60 * 24 * 7 // sec * min * hour * day

    return 1 - timeUntilNextMonday.inWholeSeconds / secondsInAWeek.toFloat()
}

fun Float.formatToFixedDecimal(len: Int = 2, separator: String = "."): String {
    val percent = this.toString()
    val list = percent.split('.')

    val wholePercent = list.getOrNull(0) ?: "0".repeat(len)

    val decimalPercent = list
        .getOrNull(1)
        ?.take(len)
        ?.padStart(len, '0') ?: "0".repeat(len)

    return "$wholePercent$separator$decimalPercent"
}

val timeZone = TimeZone.currentSystemDefault()
fun TestTimeSource.createTestClock(now: Instant? = null): Clock {
    val now = now ?: Clock.System.now()
    return this.asClock(now)
}

val LocalDateTime.Companion.DateTimeFormater: DateTimeFormat<LocalDateTime>
    get() = this.Format {
        day()
        char('/')
        monthNumber()
        char('/')
        year()
        char(' ')
        hour()
        char(':')
        minute()
        char(':')
        second()
    }
