package com.gabrielbmoro.moviedb.desingsystem.images

import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import io.kamel.image.KamelImage
import io.kamel.image.asyncPainterResource
import moviedb_android.design_system.generated.resources.Res
import moviedb_android.design_system.generated.resources.ic_movie
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource

@OptIn(ExperimentalResourceApi::class)
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
            modifier = modifier
        )
    } else {
        Image(
            painter = painterResource(Res.drawable.ic_movie),
            contentScale = contentScale,
            alignment = Alignment.TopCenter,
            contentDescription = contentDescription,
            modifier = modifier
        )
    }
}
