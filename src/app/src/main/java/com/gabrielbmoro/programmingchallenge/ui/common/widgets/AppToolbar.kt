package com.gabrielbmoro.programmingchallenge.ui.common.widgets

import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.gabrielbmoro.programmingchallenge.R
import com.gabrielbmoro.programmingchallenge.ui.common.theme.MovieDBAppTheme


@Composable
fun AppToolbar(
    title: String,
    backEvent: (() -> Unit)?,
    settingsEvent: (() -> Unit)?
) {
    val navigationIcon: @Composable (() -> Unit)? = backEvent?.let {
        {
            IconButton(onClick = backEvent) {
                Icon(
                    imageVector = Icons.Filled.ArrowBack,
                    contentDescription = stringResource(id = R.string.back)
                )
            }
        }
    }

    TopAppBar(
        title = {
            Text(title)
        },
        navigationIcon = navigationIcon,
        actions = {
            settingsEvent?.let {
                IconButton(onClick = it) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_gear),
                        contentDescription = stringResource(id = R.string.settings)
                    )
                }
            }
        }
    )
}

@Preview
@Composable
fun AppToolbarPreview() {
    MovieDBAppTheme {
        AppToolbar("Jumangi", null, null)
    }
}