package com.gabrielbmoro.moviedb.core.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

@Composable
fun MovieDBAppTheme(
    content: @Composable () -> Unit
) {
    val context = LocalContext.current
    val isSystemInDarkTheme = isSystemInDarkTheme()
    val hasDynamicColorsFeature = (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S)

    val colorScheme = when {
        hasDynamicColorsFeature -> {
            if (isSystemInDarkTheme) dynamicDarkColorScheme(context)
            else dynamicLightColorScheme(context)
        }

        else -> {
            if (isSystemInDarkTheme) movieDBDarkColorScheme
            else movieDBLightColorScheme
        }
    }

    MaterialTheme(
        content = content,
        colorScheme = colorScheme
    )
}
