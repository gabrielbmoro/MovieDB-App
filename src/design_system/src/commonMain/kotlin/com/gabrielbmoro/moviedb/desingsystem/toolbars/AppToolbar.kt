package com.gabrielbmoro.moviedb.desingsystem.toolbars

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
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
import androidx.compose.ui.text.style.TextOverflow
import moviedb_android.design_system.generated.resources.Res
import moviedb_android.design_system.generated.resources.back
import moviedb_android.design_system.generated.resources.search
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.stringResource

@OptIn(ExperimentalResourceApi::class)
@Composable
private fun BackNavigationIcon(event: (() -> Unit), modifier: Modifier = Modifier) {
    IconButton(onClick = event, modifier = modifier) {
        Icon(
            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
            contentDescription = stringResource(Res.string.back)
        )
    }
}

@OptIn(ExperimentalResourceApi::class)
@Composable
private fun SearchNavigationIcon(event: () -> Unit, modifier: Modifier = Modifier) {
    IconButton(onClick = event, modifier = modifier) {
        Icon(
            imageVector = Icons.Filled.Search,
            contentDescription = stringResource(Res.string.search)
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
    val backNavigationIcon: @Composable (() -> Unit) = if (backEvent != null) {
        {
            BackNavigationIcon(backEvent)
        }
    } else {
        {}
    }

    TopAppBar(
        title = {
            title()
        },
        navigationIcon = backNavigationIcon,
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer
        ),
        actions = {
            if (searchEvent != null) {
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
    val backNavigationIcon: @Composable (() -> Unit) = if (backEvent != null) {
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
                overflow = TextOverflow.Ellipsis
            )
        },
        navigationIcon = backNavigationIcon,
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer
        ),
        actions = {
            if (searchEvent != null) {
                SearchNavigationIcon(event = searchEvent)
            }
        }
    )
}
