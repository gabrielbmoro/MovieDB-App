package com.gabrielbmoro.moviedb.desingsystem.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable

@Composable
fun MovieDBAppTheme(
    dynamicColorScheme: ColorScheme? = null,
    content: @Composable () -> Unit,
) {
    val colorScheme =
        dynamicColorScheme ?: if (isSystemInDarkTheme()) {
            movieDBDarkColorScheme
        } else {
            movieDBLightColorScheme
        }

    MaterialTheme(
        content = content,
        colorScheme = colorScheme,
    )
}
