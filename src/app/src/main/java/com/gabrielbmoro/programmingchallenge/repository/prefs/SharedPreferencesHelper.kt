package com.gabrielbmoro.programmingchallenge.repository.prefs

import android.content.SharedPreferences
import javax.inject.Inject

class SharedPreferencesHelper @Inject constructor(
    private val preferences: SharedPreferences
) {

    fun putThemeBaseCode(themeBaseCode: Int) {
        preferences.edit()
            .putInt(THEME_KEY_VALUE, themeBaseCode)
            .apply()
    }

    fun getThemeBaseCode() = preferences.getInt(THEME_KEY_VALUE, -1)

    companion object {
        private const val THEME_KEY_VALUE = "themeBaseCode"
    }
}