package com.gabrielbmoro.programmingchallenge.presentation.settings

import android.os.Bundle
import androidx.appcompat.app.AppCompatDelegate
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import com.gabrielbmoro.programmingchallenge.R
import com.gabrielbmoro.programmingchallenge.presentation.util.setDefaultNightModeAccordingToTheValue

class SettingsPreferenceFragment : PreferenceFragmentCompat() {

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.preferences, rootKey)
    }

    override fun onDisplayPreferenceDialog(preference: Preference?) {
        if (preference is DialogPreferenceImpl) {
            //show the dialog
            val currentTheme = preferenceManager?.sharedPreferences?.getInt(
                getThemePreferenceKey(),
                AppCompatDelegate.MODE_NIGHT_NO
            )
                ?: AppCompatDelegate.MODE_NIGHT_NO
            val dialog = ThemeSelectionDialog(currentTheme)
            parentFragmentManager.setFragmentResultListener(
                ThemeSelectionDialog.SELECTED_THEME_REQUEST_KEY,
                viewLifecycleOwner
            ) { requestKey, result ->
                if (requestKey == ThemeSelectionDialog.SELECTED_THEME_REQUEST_KEY) {
                    preferenceManager?.sharedPreferences?.edit()?.apply {
                        val selectedTheme =
                            result.getInt(ThemeSelectionDialog.THEME_BUNDLE_KEY, 0)
                        val isModificationApplied =
                            requireActivity().setDefaultNightModeAccordingToTheValue(
                                selectedTheme
                            )
                        if (isModificationApplied) {
                            putInt(getThemePreferenceKey(), selectedTheme)
                            this.apply()
                        }
                    }
                }
            }
            dialog.show(parentFragmentManager)
        }
    }

    private fun getThemePreferenceKey() = getString(R.string.pref_nigh_mode_key)
}