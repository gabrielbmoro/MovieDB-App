package com.gabrielbmoro.moviedb.core.ui.widgets

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import com.gabrielbmoro.moviedb.core.R
import com.gabrielbmoro.moviedb.core.ui.theme.MovieDBAppTheme
import com.gabrielbmoro.moviedb.core.ui.theme.ThemePreviews

@Composable
private fun BackNavigationIcon(event: (() -> Unit), modifier: Modifier = Modifier) {
    IconButton(onClick = event, modifier = modifier) {
        Icon(
            imageVector = Icons.Filled.ArrowBack,
            contentDescription = stringResource(id = R.string.back)
        )
    }
}

@Composable
private fun SearchNavigationIcon(event: () -> Unit, modifier: Modifier = Modifier) {
    IconButton(onClick = event, modifier = modifier) {
        Icon(
            imageVector = Icons.Filled.Search,
            contentDescription = stringResource(id = R.string.search)
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomAppToolbar(
    title: @Composable () -> Unit,
    backEvent: (() -> Unit)? = null,
    searchEvent: (() -> Unit)? = null
) {
    val backNavigationIcon: @Composable (() -> Unit) = backEvent?.let {
        {
            BackNavigationIcon(it)
        }
    } ?: { }

    TopAppBar(
        title = {
            title()
        },
        navigationIcon = backNavigationIcon,
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer
        ),
        actions = {
            searchEvent?.let { searchEvent ->
                SearchNavigationIcon(event = searchEvent)
            }
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppToolbarTitle(
    title: String,
    backEvent: (() -> Unit)? = null,
    searchEvent: (() -> Unit)? = null
) {
    val backNavigationIcon: @Composable (() -> Unit) = backEvent?.let {
        {
            BackNavigationIcon(it)
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
        navigationIcon = backNavigationIcon,
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer
        ),
        actions = {
            searchEvent?.let { searchEvent ->
                SearchNavigationIcon(event = searchEvent)
            }
        }
    )
}

@ThemePreviews
@Composable
fun AppToolbarPreview() {
    MovieDBAppTheme {
        AppToolbarTitle("Jumangi")
    }
}
