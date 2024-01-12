package com.gabrielbmoro.moviedb.core.ui.theme

import androidx.compose.material3.ColorScheme
import androidx.compose.ui.graphics.Color

// Color scheme based on: https://github.com/android/compose-samples/blob/main/Reply/app/src/main/java/com/example/reply/ui/theme/Color.kt

val LightPrimary = Color(0xFF825500)
val LightOnPrimary = Color(0xFFFFFFFF)
val LightPrimaryContainer = Color(0xFFFFDDAE)
val LightOnPrimaryContainer = Color(0xFF2A1800)
val LightSecondary = Color(0xFF6F5B40)
val LightOnSecondary = Color(0xFFFFFFFF)
val LightSecondaryContainer = Color(0xFFFADEBC)
val LightOnSecondaryContainer = Color(0xFF271904)
val LightTertiary = Color(0xFF516440)
val LightOnTertiary = Color(0xFFFFFFFF)
val LightTertiaryContainer = Color(0xFFD3EABC)
val LightOnTertiaryContainer = Color(0xFF102004)
val LightError = Color(0xFFBA1B1B)
val LightErrorContainer = Color(0xFFFFDAD4)
val LightOnError = Color(0xFFFFFFFF)
val LightOnErrorContainer = Color(0xFF410001)
val LightBackground = Color(0xFFFCFCFC)
val LightOnBackground = Color(0xFF1F1B16)
val LightSurface = Color(0xFFFCFCFC)
val LightOnSurface = Color(0xFF1F1B16)
val LightSurfaceVariant = Color(0xFFF0E0CF)
val LightOnSurfaceVariant = Color(0xFF4F4539)
val LightOutline = Color(0xFF817567)
val LightInverseOnSurface = Color(0xFFF9EFE6)
val LightInverseSurface = Color(0xFF34302A)
val LightPrimaryInverse = Color(0xFFFFB945)

val DarkPrimary = Color(0xFFFFB945)
val DarkOnPrimary = Color(0xFF452B00)
val DarkPrimaryContainer = Color(0xFF624000)
val DarkOnPrimaryContainer = Color(0xFFFFDDAE)
val DarkSecondary = Color(0xFFDDC3A2)
val DarkOnSecondary = Color(0xFF3E2E16)
val DarkSecondaryContainer = Color(0xFF56442B)
val DarkOnSecondaryContainer = Color(0xFFFADEBC)
val DarkTertiary = Color(0xFFB8CEA2)
val DarkOnTertiary = Color(0xFF243516)
val DarkTertiaryContainer = Color(0xFF3A4C2B)
val DarkOnTertiaryContainer = Color(0xFFD3EABC)
val DarkError = Color(0xFFFFB4A9)
val DarkErrorContainer = Color(0xFF930006)
val DarkOnError = Color(0xFF680003)
val DarkOnErrorContainer = Color(0xFFFFDAD4)
val DarkBackground = Color(0xFF1F1B16)
val DarkOnBackground = Color(0xFFEAE1D9)
val DarkSurface = Color(0xFF1F1B16)
val DarkOnSurface = Color(0xFFEAE1D9)
val DarkSurfaceVariant = Color(0xFF4F4539)
val DarkOnSurfaceVariant = Color(0xFFD3C4B4)
val DarkOutline = Color(0xFF9C8F80)
val DarkInverseOnSurface = Color(0xFF32281A)
val DarkInverseSurface = Color(0xFFEAE1D9)
val DarkPrimaryInverse = Color(0xFF624000)

val movieDBDarkColorScheme = ColorScheme(
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

val movieDBLightColorScheme = ColorScheme(
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
