package com.prdo.todolist.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.painterResource
import todolist.composeapp.generated.resources.Res
import todolist.composeapp.generated.resources.check

@Composable
fun Checkbox(
    value: Boolean,
    onValueChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    val colors = MaterialTheme.colorScheme

    val tint = if (value) colors.surfaceContainerHighest else Color.Transparent
    val bgColor = if (value) colors.onSecondary else Color.Transparent
    val borderStroke = BorderStroke(
        width = 2.dp,
        brush = SolidColor(if (value) colors.onSecondary else colors.primary)
    )

    val shape = CircleShape

    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .padding(6.dp)
            .border(borderStroke, shape)
            .clickable { onValueChange(!value) }
            .background(bgColor, shape)
            .size(18.dp)
    ) {
        Icon(
            painter = painterResource(Res.drawable.check),
            contentDescription = null,
            tint = tint
        )
    }
}