package com.gabrielbmoro.moviedb.core.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.gabrielbmoro.moviedb.core.ui.theme.DarkBackground
import com.gabrielbmoro.moviedb.core.ui.theme.DarkError
import com.gabrielbmoro.moviedb.core.ui.theme.DarkErrorContainer
import com.gabrielbmoro.moviedb.core.ui.theme.DarkInverseOnSurface
import com.gabrielbmoro.moviedb.core.ui.theme.DarkInverseSurface
import com.gabrielbmoro.moviedb.core.ui.theme.DarkOnBackground
import com.gabrielbmoro.moviedb.core.ui.theme.DarkOnError
import com.gabrielbmoro.moviedb.core.ui.theme.DarkOnErrorContainer
import com.gabrielbmoro.moviedb.core.ui.theme.DarkOnPrimary
import com.gabrielbmoro.moviedb.core.ui.theme.DarkOnPrimaryContainer
import com.gabrielbmoro.moviedb.core.ui.theme.DarkOnSecondary
import com.gabrielbmoro.moviedb.core.ui.theme.DarkOnSecondaryContainer
import com.gabrielbmoro.moviedb.core.ui.theme.DarkOnSurface
import com.gabrielbmoro.moviedb.core.ui.theme.DarkOnSurfaceVariant
import com.gabrielbmoro.moviedb.core.ui.theme.DarkOnTertiary
import com.gabrielbmoro.moviedb.core.ui.theme.DarkOnTertiaryContainer
import com.gabrielbmoro.moviedb.core.ui.theme.DarkOutline
import com.gabrielbmoro.moviedb.core.ui.theme.DarkPrimary
import com.gabrielbmoro.moviedb.core.ui.theme.DarkPrimaryContainer
import com.gabrielbmoro.moviedb.core.ui.theme.DarkPrimaryInverse
import com.gabrielbmoro.moviedb.core.ui.theme.DarkSecondary
import com.gabrielbmoro.moviedb.core.ui.theme.DarkSecondaryContainer
import com.gabrielbmoro.moviedb.core.ui.theme.DarkSurface
import com.gabrielbmoro.moviedb.core.ui.theme.DarkSurfaceVariant
import com.gabrielbmoro.moviedb.core.ui.theme.DarkTertiary
import com.gabrielbmoro.moviedb.core.ui.theme.DarkTertiaryContainer
import com.gabrielbmoro.moviedb.core.ui.theme.LightBackground
import com.gabrielbmoro.moviedb.core.ui.theme.LightError
import com.gabrielbmoro.moviedb.core.ui.theme.LightErrorContainer
import com.gabrielbmoro.moviedb.core.ui.theme.LightInverseOnSurface
import com.gabrielbmoro.moviedb.core.ui.theme.LightInverseSurface
import com.gabrielbmoro.moviedb.core.ui.theme.LightOnBackground
import com.gabrielbmoro.moviedb.core.ui.theme.LightOnError
import com.gabrielbmoro.moviedb.core.ui.theme.LightOnErrorContainer
import com.gabrielbmoro.moviedb.core.ui.theme.LightOnPrimary
import com.gabrielbmoro.moviedb.core.ui.theme.LightOnPrimaryContainer
import com.gabrielbmoro.moviedb.core.ui.theme.LightOnSecondary
import com.gabrielbmoro.moviedb.core.ui.theme.LightOnSecondaryContainer
import com.gabrielbmoro.moviedb.core.ui.theme.LightOnSurface
import com.gabrielbmoro.moviedb.core.ui.theme.LightOnSurfaceVariant
import com.gabrielbmoro.moviedb.core.ui.theme.LightOnTertiary
import com.gabrielbmoro.moviedb.core.ui.theme.LightOnTertiaryContainer
import com.gabrielbmoro.moviedb.core.ui.theme.LightOutline
import com.gabrielbmoro.moviedb.core.ui.theme.LightPrimary
import com.gabrielbmoro.moviedb.core.ui.theme.LightPrimaryContainer
import com.gabrielbmoro.moviedb.core.ui.theme.LightPrimaryInverse
import com.gabrielbmoro.moviedb.core.ui.theme.LightSecondary
import com.gabrielbmoro.moviedb.core.ui.theme.LightSecondaryContainer
import com.gabrielbmoro.moviedb.core.ui.theme.LightSurface
import com.gabrielbmoro.moviedb.core.ui.theme.LightSurfaceVariant
import com.gabrielbmoro.moviedb.core.ui.theme.LightTertiary
import com.gabrielbmoro.moviedb.core.ui.theme.LightTertiaryContainer

@Composable
fun MovieDBAppTheme(
    content: @Composable () -> Unit
) {
    MaterialTheme(
        content = content,
        colorScheme = if (isSystemInDarkTheme()) ColorScheme(
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
        ) else ColorScheme(
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
    )
}
