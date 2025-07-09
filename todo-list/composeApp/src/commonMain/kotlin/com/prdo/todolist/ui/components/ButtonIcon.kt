package com.prdo.todolist.ui.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.painterResource
import todolist.composeapp.generated.resources.Res
import todolist.composeapp.generated.resources.plus
import todolist.composeapp.generated.resources.trash

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ButtonIconPlus(
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()

    val colors = MaterialTheme.colorScheme

    val animatedBgColor by animateColorAsState(
        targetValue = if (isPressed) colors.primary else colors.onPrimary,
        animationSpec = popAnimationSpec
    )

    val iconColor = colors.surfaceContainerHighest
    val shape = RoundedCornerShape(6.dp)

    Box(
        modifier = modifier
            .background(animatedBgColor, shape = shape)
            .clickable(
                interactionSource = interactionSource,
                indication = null,
                onClick = onClick
            )
            .padding(18.dp)
    ) {
        Icon(
            painter = painterResource(Res.drawable.plus),
            contentDescription = null,
            tint = iconColor
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ButtonIconTrash(
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()

    val colors = MaterialTheme.colorScheme

    val animatedBgColor by animateColorAsState(
        targetValue = if (isPressed) colors.surface else Color.Transparent,
        animationSpec = popAnimationSpec
    )
    val animatedTint by animateColorAsState(
        targetValue = if (isPressed) colors.error else colors.surfaceBright,
        animationSpec = popAnimationSpec
    )

    val shape = RoundedCornerShape(4.dp)

    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .background(animatedBgColor, shape = shape)
            .clickable(
                interactionSource = interactionSource,
                indication = null,
                onClick = onClick
            )
            .size(32.dp)
    ) {
        Icon(
            painter = painterResource(Res.drawable.trash),
            contentDescription = null,
            tint = animatedTint
        )
    }
}
