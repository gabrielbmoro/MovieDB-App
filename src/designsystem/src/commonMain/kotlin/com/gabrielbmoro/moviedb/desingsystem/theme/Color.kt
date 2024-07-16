@file:Suppress("MagicNumber")

package com.gabrielbmoro.moviedb.desingsystem.theme

import androidx.compose.material3.ColorScheme
import androidx.compose.ui.graphics.Color

// Color scheme based on: https://github.com/android/compose-samples/blob/main/Reply/app/src/main/java/com/example/reply/ui/theme/Color.kt
val LightPrimary = Color(0xFF825500L)
val LightOnPrimary = Color(0xFFFFFFFFL)
val LightPrimaryContainer = Color(0xFFFFDDAEL)
val LightOnPrimaryContainer = Color(0xFF2A1800L)
val LightSecondary = Color(0xFF6F5B40L)
val LightOnSecondary = Color(0xFFFFFFFFL)
val LightSecondaryContainer = Color(0xFFFADEBCL)
val LightOnSecondaryContainer = Color(0xFF271904L)
val LightTertiary = Color(0xFF516440L)
val LightOnTertiary = Color(0xFFFFFFFFL)
val LightTertiaryContainer = Color(0xFFD3EABCL)
val LightOnTertiaryContainer = Color(0xFF102004L)
val LightError = Color(0xFFBA1B1BL)
val LightErrorContainer = Color(0xFFFFDAD4L)
val LightOnError = Color(0xFFFFFFFFL)
val LightOnErrorContainer = Color(0xFF410001L)
val LightBackground = Color(0xFFFCFCFCL)
val LightOnBackground = Color(0xFF1F1B16L)
val LightSurface = Color(0xFFFCFCFCL)
val LightOnSurface = Color(0xFF1F1B16L)
val LightSurfaceVariant = Color(0xFFF0E0CFL)
val LightOnSurfaceVariant = Color(0xFF4F4539L)
val LightOutline = Color(0xFF817567L)
val LightInverseOnSurface = Color(0xFFF9EFE6L)
val LightInverseSurface = Color(0xFF34302AL)
val LightPrimaryInverse = Color(0xFFFFB945L)

val DarkPrimary = Color(0xFFFFB945L)
val DarkOnPrimary = Color(0xFF452B00L)
val DarkPrimaryContainer = Color(0xFF624000L)
val DarkOnPrimaryContainer = Color(0xFFFFDDAEL)
val DarkSecondary = Color(0xFFDDC3A2L)
val DarkOnSecondary = Color(0xFF3E2E16L)
val DarkSecondaryContainer = Color(0xFF56442BL)
val DarkOnSecondaryContainer = Color(0xFFFADEBCL)
val DarkTertiary = Color(0xFFB8CEA2L)
val DarkOnTertiary = Color(0xFF243516L)
val DarkTertiaryContainer = Color(0xFF3A4C2BL)
val DarkOnTertiaryContainer = Color(0xFFD3EABCL)
val DarkError = Color(0xFFFFB4A9L)
val DarkErrorContainer = Color(0xFF930006L)
val DarkOnError = Color(0xFF680003L)
val DarkOnErrorContainer = Color(0xFFFFDAD4L)
val DarkBackground = Color(0xFF1F1B16L)
val DarkOnBackground = Color(0xFFEAE1D9L)
val DarkSurface = Color(0xFF1F1B16L)
val DarkOnSurface = Color(0xFFEAE1D9L)
val DarkSurfaceVariant = Color(0xFF4F4539L)
val DarkOnSurfaceVariant = Color(0xFFD3C4B4L)
val DarkOutline = Color(0xFF9C8F80L)
val DarkInverseOnSurface = Color(0xFF32281AL)
val DarkInverseSurface = Color(0xFFEAE1D9L)
val DarkPrimaryInverse = Color(0xFF624000L)

val movieDBDarkColorScheme =
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

val movieDBLightColorScheme =
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
