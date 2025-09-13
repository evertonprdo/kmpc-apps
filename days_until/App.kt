@file:OptIn(ExperimentalTime::class)

package com.prdo.composabletraining

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ShapeDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import composabletraining.composeapp.generated.resources.Res
import composabletraining.composeapp.generated.resources.days_until
import composabletraining.composeapp.generated.resources.hours_until
import composabletraining.composeapp.generated.resources.minutes_until
import composabletraining.composeapp.generated.resources.months_until
import composabletraining.composeapp.generated.resources.seconds_until
import composabletraining.composeapp.generated.resources.years_until
import kotlinx.datetime.DatePeriod
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.format
import kotlinx.datetime.format.char
import kotlinx.datetime.periodUntil
import kotlinx.datetime.toLocalDateTime
import org.jetbrains.compose.resources.pluralStringResource
import kotlin.time.Clock
import kotlin.time.ExperimentalTime

/**
 *  kotlinx datetime exercise
 *
 *  Days Until Event
 *
 *  Write a function that takes a LocalDate (e.g., user’s birthday)
 *  and shows how many days remain until the next occurrence of that date.
 *
 */
@Composable
fun App() {

    val events = listOf(
        LocalDate(2025, 9, 14),
        LocalDate(2025, 9, 15),
        LocalDate(2025, 9, 16),
        LocalDate(2025, 9, 17),
        LocalDate(2025, 9, 18),
        LocalDate(2025, 9, 19),
        LocalDate(2025, 9, 20),
        LocalDate(2025, 10, 20),
        LocalDate(2025, 11, 20),
        LocalDate(2025, 12, 20),
    )

    val formatter = LocalDate.Format {
        day()
        char('/')
        monthNumber()
        char('/')
        year()
    }

    val now = Clock.System.now()
        .toLocalDateTime(TimeZone.currentSystemDefault()).date

    val item: @Composable (String, String) -> Unit = { prefix, date ->

        Row(Modifier.fillMaxWidth()) {
            Text(
                text = prefix,
                color = MaterialTheme.colorScheme.onSurface
            )
            Spacer(Modifier.widthIn(min = 16.dp).weight(1f))
            Text(
                text = date,
                color = MaterialTheme.colorScheme.onSurface
            )
        }
    }

    MaterialTheme {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.fillMaxSize()
        ) {
            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier
                    .background(MaterialTheme.colorScheme.surface, ShapeDefaults.Medium)
                    .verticalScroll(rememberScrollState())
                    .width(IntrinsicSize.Max)
                    .padding(24.dp)
            ) {
                item("Today", now.format(formatter))

                HorizontalDivider(Modifier.height(8.dp))

                events.forEach {
                    val timeUntil = now.periodUntil(it)
                    item(timeUntil.toDisplay(), it.format(formatter))
                }
            }
        }
    }
}

@Composable
fun DatePeriod.toDisplay(): String {
    if (this.years > 0)
        return pluralStringResource(
            resource = Res.plurals.years_until,
            quantity = this.years,
            formatArgs = arrayOf(this.years)
        )

    if (this.months > 0)
        return pluralStringResource(
            resource = Res.plurals.months_until,
            quantity = this.months,
            formatArgs = arrayOf(this.months)
        )

    if (this.days > 0)
        return pluralStringResource(
            resource = Res.plurals.days_until,
            quantity = this.days,
            formatArgs = arrayOf(this.days)
        )

    if (this.hours > 0)
        return pluralStringResource(
            resource = Res.plurals.hours_until,
            quantity = this.hours,
            formatArgs = arrayOf(this.hours)
        )

    if (this.minutes > 0)
        return pluralStringResource(
            resource = Res.plurals.minutes_until,
            quantity = this.minutes,
            formatArgs = arrayOf(this.minutes)
        )

    return pluralStringResource(
        resource = Res.plurals.seconds_until,
        quantity = this.seconds,
        formatArgs = arrayOf(this.seconds)
    )
}