package com.gabrielbmoro.programmingchallenge.domain.model

import androidx.annotation.StringRes
import com.gabrielbmoro.programmingchallenge.R

enum class ThemeType(
    val baseCode: Int,
    @StringRes val themeTitleRes: Int
) {
    AUTOMATIC(baseCode = 1, themeTitleRes = R.string.automatic_theme_option),
    NIGHT(baseCode = 10, themeTitleRes = R.string.night_theme_option),
    LIGHT(baseCode = 11, themeTitleRes = R.string.day_theme_option),
}