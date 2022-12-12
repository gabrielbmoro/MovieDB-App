package com.gabrielbmoro.programmingchallenge.ui.screens.settings

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.gabrielbmoro.programmingchallenge.BuildConfig
import com.gabrielbmoro.programmingchallenge.ui.common.widgets.DropDownValue
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor() : ViewModel() {
    private val _uiState = mutableStateOf(
        SettingsUIState(
            themeSelection = DropDownValue(
                currentOption = "Test",
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
        return listOf(
            "Dark",
            "Ligth",
            "Automatic"
        )
    }
}