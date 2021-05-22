package com.gabrielbmoro.programmingchallenge.presentation.settings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import com.gabrielbmoro.programmingchallenge.R
import com.gabrielbmoro.programmingchallenge.databinding.DialogThemeSelectionBinding

class ThemeSelectionDialog(private val currentTheme: Int) : DialogFragment() {

    private lateinit var binding: DialogThemeSelectionBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DialogThemeSelectionBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val preferencesOptions = resources.getTextArray(R.array.pref_themes)
        if (preferencesOptions.size >= 3) {
            val optionValue = when (currentTheme) {
                0 -> preferencesOptions[0]
                AppCompatDelegate.MODE_NIGHT_YES -> preferencesOptions[1]
                AppCompatDelegate.MODE_NIGHT_NO -> preferencesOptions[2]
                else -> ""
            }
            binding.dialogThemeSelectionGroup.setCheckTo(optionValue)

            binding.dialogThemeSelectionGroup.setupCallback { selectedItem ->
                val selectedTheme = when (selectedItem) {
                    preferencesOptions[0] -> 0
                    preferencesOptions[1] -> AppCompatDelegate.MODE_NIGHT_YES
                    preferencesOptions[2] -> AppCompatDelegate.MODE_NIGHT_NO
                    else -> AppCompatDelegate.MODE_NIGHT_NO
                }
                parentFragmentManager.setFragmentResult(
                    SELECTED_THEME_REQUEST_KEY,
                    Bundle().apply {
                        putInt(THEME_BUNDLE_KEY, selectedTheme)
                    }
                )
                dismiss()
            }
        }
    }

    fun show(fragmentManager: FragmentManager) {
        super.show(fragmentManager, ThemeSelectionDialog::javaClass.name)
    }

    companion object {
        const val SELECTED_THEME_REQUEST_KEY = "selectTheme"
        const val THEME_BUNDLE_KEY = "ThemeSelectionDialog:themeKey"
    }
}