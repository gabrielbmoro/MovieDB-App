package com.gabrielbmoro.programmingchallenge.ui.common.theme

import android.content.res.Configuration
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview

@Preview(
    name = "dark mode",
    group = "themes",
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Preview(
    name = "light mode",
    group = "themes",
    uiMode = Configuration.UI_MODE_NIGHT_NO
)
annotation class ThemePreviews

@Composable
fun ThemePreview(
    content: @Composable () -> Unit
) {
    MovieDBAppTheme {
        content()
    }
}