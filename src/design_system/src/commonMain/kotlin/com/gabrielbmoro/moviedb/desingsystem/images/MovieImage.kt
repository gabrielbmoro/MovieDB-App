package com.gabrielbmoro.moviedb.desingsystem.images

import `MovieDB-Android`.resources.MR
import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import dev.icerock.moko.resources.compose.painterResource
import io.kamel.image.KamelImage
import io.kamel.image.asyncPainterResource

@Composable
fun MovieImage(
    imageUrl: String?,
    contentDescription: String,
    contentScale: ContentScale,
    modifier: Modifier = Modifier
) {
    if (imageUrl != null) {
        KamelImage(
            resource = asyncPainterResource(imageUrl),
            contentScale = contentScale,
            alignment = Alignment.TopCenter,
            contentDescription = contentDescription,
            modifier = modifier,
            onLoading = {
                Image(
                    painter = painterResource(MR.images.ic_movie_media_player),
                    contentScale = ContentScale.Inside,
                    contentDescription = contentDescription,
                    modifier = modifier
                )
            }
        )
    } else {
        Image(
            painter = painterResource(MR.images.ic_movie_media_player),
            contentScale = contentScale,
            alignment = Alignment.TopCenter,
            contentDescription = contentDescription,
            modifier = modifier
        )
    }
}
