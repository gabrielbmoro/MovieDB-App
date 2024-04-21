package com.gabrielbmoro.moviedb.desingsystem.icons

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import dev.icerock.moko.resources.compose.stringResource
import com.gabrielbmoro.moviedb.SharedRes

@Composable
fun BackNavigationIcon(
    event: (() -> Unit),
    modifier: Modifier = Modifier,
) {
    IconButton(onClick = event, modifier = modifier) {
        Icon(
            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
            contentDescription = stringResource(SharedRes.strings.back),
        )
    }
}
