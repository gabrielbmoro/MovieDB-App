package com.gabrielbmoro.programmingchallenge.ui.screens.settings

import android.annotation.SuppressLint
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.gabrielbmoro.programmingchallenge.R
import com.gabrielbmoro.programmingchallenge.ui.common.widgets.AppToolbar

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun SettingsScreen(navController: NavController, viewModel: SettingsViewModel = hiltViewModel()) {
    Scaffold(
        content = {

        },
        topBar = {
            AppToolbar(
                title = stringResource(id = R.string.settings),
                backEvent = { navController.navigateUp() },
                settingsEvent = null
            )
        }
    )
}