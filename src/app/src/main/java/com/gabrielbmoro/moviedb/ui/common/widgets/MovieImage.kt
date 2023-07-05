package com.gabrielbmoro.moviedb.ui.common.widgets

import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import coil.compose.AsyncImage
import com.gabrielbmoro.moviedb.R

@Composable
fun MovieImage(imageUrl: String?, modifier: Modifier = Modifier, contentScale: ContentScale) {

    if (imageUrl != null) {
        AsyncImage(
            model = imageUrl,
            contentScale = contentScale,
            alignment = Alignment.TopCenter,
            contentDescription = stringResource(id = R.string.poster),
            modifier = modifier
        )
    } else {
        Image(
            painter = painterResource(id = R.drawable.ic_movie),
            contentScale = contentScale,
            alignment = Alignment.TopCenter,
            contentDescription = stringResource(id = R.string.poster),
            modifier = modifier
        )
    }
}