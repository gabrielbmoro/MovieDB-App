package com.gabrielbmoro.moviedb.core.ui.mvi

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.collectLatest

@Composable
fun <L> LabelWatcherEffect(labelFlow: SharedFlow<L?>, onLabelPublished: suspend (L?) -> Unit) {
    LaunchedEffect(
        key1 = Unit,
        block = {
            labelFlow.collectLatest { label ->
                label?.let {
                    onLabelPublished(it)
                }
            }
        }
    )
}