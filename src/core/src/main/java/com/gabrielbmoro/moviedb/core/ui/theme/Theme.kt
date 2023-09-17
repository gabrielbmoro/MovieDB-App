package com.gabrielbmoro.moviedb.core.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

@Composable
fun MovieDBAppTheme(
    content: @Composable () -> Unit
) {
    MaterialTheme(
        content = content,
        colorScheme = if (isSystemInDarkTheme()) {
            ColorScheme(
                primary = DarkPrimary,
                onPrimary = DarkOnPrimary,
                primaryContainer = DarkPrimaryContainer,
                onPrimaryContainer = DarkOnPrimaryContainer,
                inversePrimary = DarkPrimaryInverse,
                secondary = DarkSecondary,
                onSecondary = DarkOnSecondary,
                secondaryContainer = DarkSecondaryContainer,
                onSecondaryContainer = DarkOnSecondaryContainer,
                tertiary = DarkTertiary,
                onTertiary = DarkOnTertiary,
                tertiaryContainer = DarkTertiaryContainer,
                onTertiaryContainer = DarkOnTertiaryContainer,
                background = DarkBackground,
                onBackground = DarkOnBackground,
                surface = DarkSurface,
                onSurface = DarkOnSurface,
                surfaceVariant = DarkSurfaceVariant,
                onSurfaceVariant = DarkOnSurfaceVariant,
                inverseSurface = DarkInverseSurface,
                inverseOnSurface = DarkInverseOnSurface,
                error = DarkError,
                onError = DarkOnError,
                errorContainer = DarkErrorContainer,
                onErrorContainer = DarkOnErrorContainer,
                outline = DarkOutline,
                outlineVariant = Color.Gray,
                scrim = Color.Gray,
                surfaceTint = Color.White
            )
        } else {
            ColorScheme(
                primary = LightPrimary,
                onPrimary = LightOnPrimary,
                primaryContainer = LightPrimaryContainer,
                onPrimaryContainer = LightOnPrimaryContainer,
                inversePrimary = LightPrimaryInverse,
                secondary = LightSecondary,
                onSecondary = LightOnSecondary,
                secondaryContainer = LightSecondaryContainer,
                onSecondaryContainer = LightOnSecondaryContainer,
                tertiary = LightTertiary,
                onTertiary = LightOnTertiary,
                tertiaryContainer = LightTertiaryContainer,
                onTertiaryContainer = LightOnTertiaryContainer,
                background = LightBackground,
                onBackground = LightOnBackground,
                surface = LightSurface,
                onSurface = LightOnSurface,
                surfaceVariant = LightSurfaceVariant,
                onSurfaceVariant = LightOnSurfaceVariant,
                inverseSurface = LightInverseSurface,
                inverseOnSurface = LightInverseOnSurface,
                error = LightError,
                onError = LightOnError,
                errorContainer = LightErrorContainer,
                onErrorContainer = LightOnErrorContainer,
                outline = LightOutline,
                outlineVariant = Color.White,
                scrim = Color.White,
                surfaceTint = Color.Gray
            )
        }
    )
}
