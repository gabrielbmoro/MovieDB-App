package com.gabrielbmoro.programmingchallenge.ui.screens.settings

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.gabrielbmoro.programmingchallenge.BuildConfig
import com.gabrielbmoro.programmingchallenge.R
import com.gabrielbmoro.programmingchallenge.core.providers.resources.ResourcesProvider
import com.gabrielbmoro.programmingchallenge.ui.common.widgets.DropDownValue
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val resourcesProvider: ResourcesProvider,
) : ViewModel() {
    private val _uiState = mutableStateOf(
        SettingsUIState(
            themeSelection = DropDownValue(
                currentOption = resourcesProvider.getString(R.string.automatic_theme_option),
                expanded = false
            )
        )
    )
    val uiState: MutableState<SettingsUIState> = _uiState

    val appVersion: String
        get() = "${BuildConfig.VERSION_NAME} - (${BuildConfig.VERSION_CODE})"

    fun changeTheme(it: DropDownValue<String>) {
        _uiState.value = _uiState.value.copy(themeSelection = it)
    }

    fun availableThemes(): List<String> {
        return resourcesProvider.getArray(R.array.themes).toList()
    }
}