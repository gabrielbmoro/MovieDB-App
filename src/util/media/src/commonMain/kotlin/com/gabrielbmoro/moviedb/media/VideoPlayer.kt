package com.gabrielbmoro.moviedb.media

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
expect fun VideoPlayer(
    videoId: String,
    modifier: Modifier = Modifier,
)

internal fun String.videoIdToEmbedHTML(): String {
    return """
        <iframe width="100%" height="100%"
            src="https://www.youtube.com/embed/$this?playsinline=1"
            frameborder="0" allowfullscreen referrerpolicy="strict-origin-when-cross-origin"/>
    """.trimIndent()
}

internal const val MOVIE_DB_DOMAIN = "https://com.gabrielbmoro.com/moviedbapp"
