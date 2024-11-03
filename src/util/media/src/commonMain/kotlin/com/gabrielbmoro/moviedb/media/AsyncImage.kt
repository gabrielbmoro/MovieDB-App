package com.gabrielbmoro.moviedb.media

import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.BoxWithConstraintsScope
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.FilterQuality
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import io.kamel.core.Resource
import io.kamel.image.KamelImage
import io.kamel.image.asyncPainterResource

@Suppress("LongParameterList")
@Composable
fun AsyncImage(
    imageUrl: String,
    contentDescription: String?,
    contentScale: ContentScale,
    onLoading: (@Composable BoxScope.(Float) -> Unit)? = null,
    onFailure: (@Composable BoxScope.(Throwable) -> Unit)? = null,
    filterQuality: FilterQuality = FilterQuality.High,
    modifier: Modifier = Modifier
) {
    val getPainterResource: @Composable (BoxWithConstraintsScope.() -> Resource<Painter>) = {
        asyncPainterResource(
            imageUrl,
            filterQuality = filterQuality,
        )
    }
    KamelImage(
        resource = getPainterResource,
        contentScale = contentScale,
        alignment = Alignment.TopCenter,
        contentDescription = contentDescription,
        modifier = modifier,
        onLoading = onLoading,
        onFailure = onFailure
    )
}
