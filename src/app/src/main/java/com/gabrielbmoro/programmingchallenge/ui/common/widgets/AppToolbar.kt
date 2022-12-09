package com.gabrielbmoro.programmingchallenge.ui.common.widgets

import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.gabrielbmoro.programmingchallenge.R
import com.gabrielbmoro.programmingchallenge.ui.common.theme.MovieDBAppTheme


@Composable
fun AppToolbar(title: String, backEvent: (() -> Unit)?) {
    if (backEvent == null) {
        TopAppBar(
            title = {
                Text(title)
            },
        )
    } else {
        TopAppBar(
            title = {
                Text(title)
            },
            navigationIcon = {
                IconButton(onClick = backEvent) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = stringResource(id = R.string.back)
                    )
                }
            }
        )
    }
}

@Preview
@Composable
fun AppToolbarPreview() {
    MovieDBAppTheme {
        AppToolbar("Jumangi", null)
    }
}