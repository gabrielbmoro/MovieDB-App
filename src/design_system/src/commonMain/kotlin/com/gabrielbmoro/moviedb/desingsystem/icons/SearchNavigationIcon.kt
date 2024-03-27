package com.gabrielbmoro.moviedb.desingsystem.icons

import `MovieDB-Android`.resources.MR
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import dev.icerock.moko.resources.compose.stringResource

@Composable
fun SearchNavigationIcon(event: () -> Unit, modifier: Modifier = Modifier) {
    IconButton(onClick = event, modifier = modifier) {
        Icon(
            imageVector = Icons.Filled.Search,
            contentDescription = stringResource(MR.strings.search)
        )
    }
}