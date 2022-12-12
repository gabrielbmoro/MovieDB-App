package com.gabrielbmoro.programmingchallenge.ui.screens.settings

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.gabrielbmoro.programmingchallenge.BuildConfig
import com.gabrielbmoro.programmingchallenge.R
import com.gabrielbmoro.programmingchallenge.core.providers.resources.ResourcesProvider
import com.gabrielbmoro.programmingchallenge.domain.model.ThemeType
import com.gabrielbmoro.programmingchallenge.domain.usecases.GetSelectedThemeUseCase
import com.gabrielbmoro.programmingchallenge.domain.usecases.SelectThemeUseCase
import com.gabrielbmoro.programmingchallenge.ui.common.widgets.DropDownValue
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val resourcesProvider: ResourcesProvider,
    private val selectThemeUseCase: SelectThemeUseCase,
    private val getSelectedThemeUseCase: GetSelectedThemeUseCase
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

    fun changeTheme(newTheme: DropDownValue<String>) {
        _uiState.value = _uiState.value.copy(
            themeSelection = newTheme,
        )
    }

    fun availableThemes(): List<String> {
        return resourcesProvider.getArray(R.array.themes).toList()
    }

    fun syncSelectedTheme() {
        getSelectedThemeUseCase.invoke()?.let { currentTheme ->
            val themeName = resourcesProvider.getString(currentTheme.themeTitleRes)
            _uiState.value = _uiState.value.copy(
                themeSelection = _uiState.value.themeSelection.copy(
                    currentOption = themeName
                )
            )
        }
    }

    fun saveNewChanges() {
        val newTheme = ThemeType.values().firstOrNull { resourcesProvider.getString(it.themeTitleRes) == _uiState.value.themeSelection.currentOption }
        newTheme?.let { theme ->
            selectThemeUseCase.invoke(theme)
            _uiState.value = _uiState.value.copy(
                alertMessage = resourcesProvider.getString(R.string.set_theme_message)
            )
        }
    }

    fun agreeWithAlertMessage() {
        _uiState.value = _uiState.value.copy(
            alertMessage = null
        )
    }
}