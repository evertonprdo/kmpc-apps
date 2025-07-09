package com.prdo.todolist.ui.components

import androidx.compose.animation.core.AnimationSpec
import androidx.compose.animation.core.EaseIn
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.ui.graphics.Color

val fastOutSlowInAnimationSpec: AnimationSpec<Color> =
    tween(durationMillis = 300, easing = FastOutSlowInEasing)

val popAnimationSpec: AnimationSpec<Color> =
    tween(durationMillis = 100, easing = EaseIn)