package com.gabrielbmoro.moviedb.desingsystem.toolbars

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.runtime.Composable

@Composable
fun AnimatedAppToolbar(
    showTopBar: Boolean,
    appBar: @Composable () -> Unit,
) {
    AnimatedVisibility(
        visible = showTopBar,
        enter = expandVertically(
            tween(delayMillis = 200, durationMillis = 500),
        ),
        exit = shrinkVertically(
            targetHeight = { fullHeight ->
                fullHeight.div(2)
            },
        ),
    ) {
        appBar()
    }
}
