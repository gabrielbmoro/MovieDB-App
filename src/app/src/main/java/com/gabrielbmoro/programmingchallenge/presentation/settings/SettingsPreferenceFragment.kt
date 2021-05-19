package com.gabrielbmoro.programmingchallenge.presentation.settings

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatDelegate
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import com.gabrielbmoro.programmingchallenge.R
import com.gabrielbmoro.programmingchallenge.presentation.util.setDefaultNightModeAccordingToTheValue

class SettingsPreferenceFragment : PreferenceFragmentCompat() {

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.preferences, rootKey);
    }

    override fun onDisplayPreferenceDialog(preference: Preference?) {
        if (preference is DialogPreferenceImpl) {
            //show the dialog
            val currentTheme = preferenceManager?.sharedPreferences?.getInt(getThemePreferenceKey(), AppCompatDelegate.MODE_NIGHT_NO)
                    ?: AppCompatDelegate.MODE_NIGHT_NO
            val dialog = ThemeSelectionDialog(currentTheme)
            dialog.setTargetFragment(this@SettingsPreferenceFragment)
            dialog.show(parentFragmentManager)
        }
    }

    private fun getThemePreferenceKey() = getString(R.string.pref_nigh_mode_key)

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == ThemeSelectionDialog.REQUEST_CODE) {
            activity?.also { act ->
                preferenceManager?.sharedPreferences?.edit()?.apply {
                    val isModificationApplied = act.setDefaultNightModeAccordingToTheValue(resultCode)
                    if (isModificationApplied) {
                        putInt(getThemePreferenceKey(), resultCode)
                        this.apply()
                    }
                }
            }

        }
        super.onActivityResult(requestCode, resultCode, data)
    }
}