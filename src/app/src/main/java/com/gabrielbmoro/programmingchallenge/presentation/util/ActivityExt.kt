package com.gabrielbmoro.programmingchallenge.presentation.util

import android.app.Activity
import android.content.Context
import androidx.appcompat.app.AppCompatDelegate
import com.gabrielbmoro.programmingchallenge.R

fun Activity.setDefaultNightModeAccordingToTheValue(value: Int): Boolean {
    val availableThemes = listOf(AppCompatDelegate.MODE_NIGHT_YES, AppCompatDelegate.MODE_NIGHT_NO, AppCompatDelegate.MODE_NIGHT_AUTO)
    return if (availableThemes.contains(value)) {
        AppCompatDelegate.setDefaultNightMode(value)
        this.recreate()
        true
    } else
        false
}

fun Activity.setThemeAccordingToThePreferences() {
    val themeKey = getString(R.string.pref_nigh_mode_key)
    val value = getSharedPreferences("${application.applicationInfo.packageName}_preferences", Context.MODE_PRIVATE).getInt(themeKey, -1)
    if (value != INVALID_INDEX) {
        AppCompatDelegate.setDefaultNightMode(value)
    }
}

const val INVALID_INDEX = -1