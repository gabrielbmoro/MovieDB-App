package com.gabrielbmoro.programmingchallenge.ui.screens.settings

import com.gabrielbmoro.programmingchallenge.ui.common.widgets.DropDownValue

data class SettingsUIState(
    val themeSelection: DropDownValue<String>,
    val alertMessage: String? = null
)