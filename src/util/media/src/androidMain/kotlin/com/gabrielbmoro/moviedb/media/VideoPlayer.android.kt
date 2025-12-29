package com.gabrielbmoro.moviedb.media

import android.annotation.SuppressLint
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView

@SuppressLint("SetJavaScriptEnabled")
@Composable
actual fun VideoPlayer(
    videoId: String,
    modifier: Modifier,
) {
    val context = LocalContext.current
    val webView = remember {
        WebView(context).apply {
            layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT,
            )
            webViewClient = WebViewClient()
            settings.javaScriptEnabled = true
            settings.useWideViewPort = true
        }
    }

    AndroidView(
        modifier = modifier,
        factory = {
            webView
        },
    )

    LaunchedEffect(Unit) {
        val embedHTML = "<html>" +
            "<head>" +
            "<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">" +
            "</head>" +
            "<body>" +
            videoId.videoIdToEmbedHTML() +
            "</body>" +
            "</html>"

        webView.loadDataWithBaseURL(
            MOVIE_DB_DOMAIN,
            embedHTML,
            "text/html",
            "UTF-8",
            null,
        )
    }
}
