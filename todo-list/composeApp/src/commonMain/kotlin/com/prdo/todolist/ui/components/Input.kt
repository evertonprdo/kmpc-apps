package com.prdo.todolist.ui.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.unit.dp


@Composable
fun Input(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    placeholder: String = "",
) {
    var isFocused by remember { mutableStateOf(false) }

    val colors = MaterialTheme.colorScheme

    val bgColor = colors.surfaceDim
    val textStyle = MaterialTheme.typography.bodyLarge.copy(color = colors.surfaceContainerHighest)

    val borderShape = RoundedCornerShape(8.dp)
    val animatedBorderColor by animateColorAsState(
        targetValue = if (isFocused) colors.onSecondary else colors.surfaceContainerLowest,
        animationSpec = fastOutSlowInAnimationSpec
    )

    Box(
        modifier = modifier
            .border(width = 1.dp, color = animatedBorderColor, shape = borderShape)
            .background(color = bgColor, shape = borderShape)
            .padding(16.dp)
    ) {
        BasicTextField(
            value = value,
            onValueChange = onValueChange,
            singleLine = true,
            textStyle = textStyle,
            cursorBrush = SolidColor(textStyle.color),
            modifier = Modifier
                .fillMaxWidth()
                .onFocusChanged { isFocused = it.isFocused }

        ) { innerTextField ->
            val placeholderTextStyle = textStyle.copy(color = colors.surfaceBright)

            Box {
                if (!isFocused && value.isEmpty()) {
                    Text(
                        text = placeholder,
                        style = placeholderTextStyle
                    )
                }
                innerTextField()
            }
        }
    }
}

