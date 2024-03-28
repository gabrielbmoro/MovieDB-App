package com.gabrielbmoro.moviedb.desingsystem.toolbars

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.style.TextOverflow
import com.gabrielbmoro.moviedb.desingsystem.icons.BackNavigationIcon
import com.gabrielbmoro.moviedb.desingsystem.icons.SearchNavigationIcon

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppToolbarTitle(
    title: String,
    backEvent: (() -> Unit)? = null,
    searchEvent: (() -> Unit)? = null,
) {
    val backNavigationIcon: @Composable (() -> Unit) =
        if (backEvent != null) {
            {
                BackNavigationIcon(backEvent)
            }
        } else {
            {}
        }

    TopAppBar(
        title = {
            Text(
                title,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )
        },
        navigationIcon = backNavigationIcon,
        colors =
            TopAppBarDefaults.topAppBarColors(
                containerColor = MaterialTheme.colorScheme.primaryContainer,
            ),
        actions = {
            if (searchEvent != null) {
                SearchNavigationIcon(event = searchEvent)
            }
        },
    )
}
