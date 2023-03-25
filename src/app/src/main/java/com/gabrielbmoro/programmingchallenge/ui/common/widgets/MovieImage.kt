package com.gabrielbmoro.programmingchallenge.ui.common.widgets

import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import coil.compose.AsyncImage
import com.gabrielbmoro.programmingchallenge.R

private const val DEFAULT_IMAGE =
    "https://wholefully.com/wp-content/uploads/2017/06/movie-theatre-popcorn-800x1200-720x540.jpg"

@Composable
fun MovieImage(imageUrl: String?, modifier: Modifier = Modifier, contentScale: ContentScale) {
    val url = imageUrl ?: DEFAULT_IMAGE

    AsyncImage(
        model = url,
        contentScale = contentScale,
        alignment = Alignment.TopCenter,
        contentDescription = stringResource(id = R.string.poster),
        modifier = modifier
    )
}