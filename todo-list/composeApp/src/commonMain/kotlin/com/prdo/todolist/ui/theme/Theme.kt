package com.prdo.todolist.ui.theme

import androidx.compose.material3.ColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val colorScheme = ColorScheme(
    primary = blue,
    onPrimary = blueDark,

    primaryContainer = Color.Unspecified,
    onPrimaryContainer = Color.Unspecified,
    inversePrimary = Color.Unspecified,

    secondary = purple,
    onSecondary = purpleDark,

    secondaryContainer = Color.Unspecified,
    onSecondaryContainer = Color.Unspecified,
    tertiary = Color.Unspecified,
    onTertiary = Color.Unspecified,
    tertiaryContainer = Color.Unspecified,
    onTertiaryContainer = Color.Unspecified,

    background = gray600,
    onBackground = gray100,

    surfaceVariant = Color.Unspecified,
    onSurfaceVariant = Color.Unspecified,
    surfaceTint = Color.Unspecified,
    inverseSurface = Color.Unspecified,
    inverseOnSurface = Color.Unspecified,

    error = danger,

    onError = Color.Unspecified,
    errorContainer = Color.Unspecified,
    onErrorContainer = Color.Unspecified,

    outline = gray400,
    outlineVariant = Color.Unspecified,

    scrim = Color.Unspecified,
    onSurface = Color.Unspecified,
    surfaceContainer = Color.Unspecified,

    surfaceContainerLowest = gray700,
    surfaceContainerLow = gray600,
    surfaceDim = gray500,
    surface = gray400,
    surfaceBright = gray300,
    surfaceContainerHigh = gray200,
    surfaceContainerHighest = gray100,
)


@Composable
fun AppTheme(
    content: @Composable() () -> Unit
) {
    MaterialTheme(
        colorScheme = colorScheme,
        typography = getAppTypography(),
        content = content,
    )
}