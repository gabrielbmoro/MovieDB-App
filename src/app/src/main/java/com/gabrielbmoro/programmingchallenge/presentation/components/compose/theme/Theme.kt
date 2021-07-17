package com.gabrielbmoro.programmingchallenge.presentation.components.compose.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color


private val LightThemeColors = lightColors(
    primary = Purple500,
    primaryVariant = Blue700,
    secondary = Teal200,
    surface = Color.White,
    onSurface = Color.Black
)

private val DarkThemeColors = darkColors(
    primary = Blue700,
    primaryVariant = Purple500,
    secondary = Color.White,
    surface = Color.Black,
    onSurface = Color.White
)

@Composable
fun MovieDBAppTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colors = if (darkTheme) DarkThemeColors else LightThemeColors,
        content = content
    )
}
