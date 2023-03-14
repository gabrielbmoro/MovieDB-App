package com.gabrielbmoro.programmingchallenge.ui.common.widgets

import androidx.annotation.DrawableRes
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import com.gabrielbmoro.programmingchallenge.R
import com.gabrielbmoro.programmingchallenge.ui.common.theme.MovieDBAppTheme

data class ExtraEvent(
    @DrawableRes val icon: Int,
    val action: (() -> Unit),
    val contentDescription: String,
)

@Composable
fun AppToolbar(
    title: String,
    backEvent: (() -> Unit)?,
    extraEvent: ExtraEvent? = null
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
            Text(
                title,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        },
        navigationIcon = navigationIcon,
        actions = {
            extraEvent?.let {
                IconButton(onClick = extraEvent.action) {
                    Icon(
                        painter = painterResource(id = extraEvent.icon),
                        contentDescription = extraEvent.contentDescription
                    )
                }
            }
        }
    )
}

@Preview
@Composable
fun AppToolbarPreview() {
    MovieDBAppTheme(false) {
        AppToolbar("Jumangi", null, null)
    }
}