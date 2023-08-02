package com.gabrielbmoro.moviedb.ui.common.widgets

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import com.gabrielbmoro.moviedb.R
import com.gabrielbmoro.moviedb.ui.common.theme.MovieDBAppTheme
import com.gabrielbmoro.moviedb.ui.common.theme.ThemePreviews

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppToolbar(
    title: String,
    backEvent: (() -> Unit)? = null,
) {
    val navigationIcon: @Composable (() -> Unit) = backEvent?.let {
        {
            IconButton(onClick = backEvent) {
                Icon(
                    imageVector = Icons.Filled.ArrowBack,
                    contentDescription = stringResource(id = R.string.back)
                )
            }
        }
    } ?: { }

    TopAppBar(
        title = {
            Text(
                title,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        },
        navigationIcon = navigationIcon,
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer
        )
    )
}

@ThemePreviews
@Composable
fun AppToolbarPreview() {
    MovieDBAppTheme {
        AppToolbar("Jumangi")
    }
}