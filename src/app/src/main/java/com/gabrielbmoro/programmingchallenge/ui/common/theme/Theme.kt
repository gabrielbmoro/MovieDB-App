package com.gabrielbmoro.programmingchallenge.ui.common.theme

import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable

private val LightThemeColors = lightColors(
    primary = Purple500,
    primaryVariant = Blue700,
)

private val DarkThemeColors = darkColors(
    primary = Blue700,
    primaryVariant = Purple500,
)

@Composable
fun MovieDBAppTheme(
    darkTheme: Boolean,
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colors = if (darkTheme) DarkThemeColors else LightThemeColors,
        content = content
    )
}
