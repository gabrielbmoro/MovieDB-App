package com.gabrielbmoro.moviedb.ui.common.widgets

import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.gabrielbmoro.moviedb.R

@Composable
fun MovieImage(
    imageUrl: String?,
    contentDescription: String,
    contentScale: ContentScale,
    modifier: Modifier = Modifier,
) {

    if (imageUrl != null) {
        val context = LocalContext.current
        val imageRequest = ImageRequest.Builder(context)
            .placeholder(R.drawable.ic_movie)
            .data(imageUrl)
            .build()

        AsyncImage(
            model = imageRequest,
            contentScale = contentScale,
            alignment = Alignment.TopCenter,
            contentDescription = contentDescription,
            modifier = modifier
        )
    } else {
        Image(
            painter = painterResource(id = R.drawable.ic_movie),
            contentScale = contentScale,
            alignment = Alignment.TopCenter,
            contentDescription = contentDescription,
            modifier = modifier
        )
    }
}