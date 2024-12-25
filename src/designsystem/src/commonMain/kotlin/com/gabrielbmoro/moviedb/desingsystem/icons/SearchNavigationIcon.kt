package com.gabrielbmoro.moviedb.desingsystem.icons

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import moviedbapp.designsystem.generated.resources.Res
import moviedbapp.designsystem.generated.resources.search
import org.jetbrains.compose.resources.stringResource

@Composable
fun SearchNavigationIcon(
    event: () -> Unit,
    modifier: Modifier = Modifier
) {
    IconButton(onClick = event, modifier = modifier) {
        Icon(
            imageVector = Icons.Filled.Search,
            contentDescription = stringResource(Res.string.search)
        )
    }
}
