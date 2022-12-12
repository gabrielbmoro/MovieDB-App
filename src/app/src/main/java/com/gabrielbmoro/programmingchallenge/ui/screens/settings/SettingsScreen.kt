package com.gabrielbmoro.programmingchallenge.ui.screens.settings

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment.Companion.BottomCenter
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Alignment.Companion.TopCenter
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.gabrielbmoro.programmingchallenge.R
import com.gabrielbmoro.programmingchallenge.ui.common.widgets.Alert
import com.gabrielbmoro.programmingchallenge.ui.common.widgets.AppToolbar
import com.gabrielbmoro.programmingchallenge.ui.common.widgets.CommonDropDown
import com.gabrielbmoro.programmingchallenge.ui.common.widgets.ExtraEvent

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun SettingsScreen(navController: NavController, viewModel: SettingsViewModel = hiltViewModel()) {
    val uiState = remember { viewModel.uiState }

    Scaffold(
        content = {
            Box(
                Modifier
                    .padding(
                        horizontal = 16.dp,
                        vertical = 24.dp
                    )
                    .fillMaxSize()
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(TopCenter)
                ) {
                    Text(
                        modifier = Modifier
                            .align(CenterVertically),
                        text = stringResource(id = R.string.theme),
                        style = MaterialTheme.typography.subtitle1
                    )

                    Spacer(
                        modifier = Modifier
                            .width(12.dp)
                            .align(CenterVertically)
                    )

                    CommonDropDown(
                        modifier = Modifier
                            .align(CenterVertically),
                        options = viewModel.availableThemes(),
                        currentValue = uiState.value.themeSelection,
                        onValueChanged = {
                            viewModel.changeTheme(it)
                        }
                    )
                }

                uiState.value.alertMessage?.let { alertMessage ->
                    Alert(
                        title = stringResource(id = R.string.alert),
                        message = alertMessage,
                        positiveText = stringResource(id = android.R.string.ok),
                        positiveAct = { viewModel.agreeWithAlertMessage() },
                        onDismissRequest = { viewModel.agreeWithAlertMessage() }
                    )
                }

                Text(
                    text = viewModel.appVersion,
                    modifier = Modifier
                        .align(BottomCenter)
                )
            }
        },
        topBar = {
            AppToolbar(
                title = stringResource(id = R.string.settings),
                backEvent = { navController.navigateUp() },
                extraEvent = ExtraEvent(
                    icon = R.drawable.ic_floppy,
                    action = {
                        viewModel.saveNewChanges()
                    },
                    contentDescription = stringResource(id = R.string.save)
                )
            )
        }
    )

    LaunchedEffect(
        key1 = Unit,
        block = {
            viewModel.syncSelectedTheme()
        }
    )
}