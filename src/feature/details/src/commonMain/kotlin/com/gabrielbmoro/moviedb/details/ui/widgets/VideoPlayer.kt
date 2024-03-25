package com.gabrielbmoro.moviedb.details.ui.widgets

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
expect fun VideoPlayer(
    videoId: String,
    shouldStartMuted: Boolean,
    onFullScreenEvent: ((String) -> Unit)?,
    modifier: Modifier = Modifier
)