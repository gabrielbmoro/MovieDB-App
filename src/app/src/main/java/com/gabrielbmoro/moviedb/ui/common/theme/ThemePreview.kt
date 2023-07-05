package com.gabrielbmoro.moviedb.ui.common.theme

import android.content.res.Configuration
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

