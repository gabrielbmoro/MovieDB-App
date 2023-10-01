package com.gabrielbmoro.moviedb.details.ui.screens.fullscreen

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import com.gabrielbmoro.moviedb.details.ui.widgets.VideoPlayer

class FullScreenActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val videoId = intent.getStringExtra(FULL_SCREEN_VIDEO_ID_INTENT_KEY)
            ?: throw IllegalStateException("It is required video id argument")

        setContent {
            VideoPlayer(
                videoId = videoId,
                onFullScreenEvent = null,
                shouldStartMuted = false,
                modifier = Modifier.fillMaxSize()
            )
        }
    }

    companion object {
        private const val FULL_SCREEN_VIDEO_ID_INTENT_KEY = "fullScreenVideoId"

        fun launchIntent(context: Context, videoId: String) =
            Intent(context, FullScreenActivity::class.java).apply {
                putExtra(FULL_SCREEN_VIDEO_ID_INTENT_KEY, videoId)
            }
    }
}
