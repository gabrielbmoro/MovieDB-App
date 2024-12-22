package com.gabrielbmoro.moviedb.media

import android.view.ViewGroup
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.PlayerConstants
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.YouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.options.IFramePlayerOptions
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView

@Composable
actual fun VideoPlayer(
    videoId: String,
    shouldStartMuted: Boolean,
    modifier: Modifier
) {
    val context = LocalContext.current

    var youtubePlayer: YouTubePlayerView? =
        remember {
            YouTubePlayerView(
                context
            ).apply {
                enableAutomaticInitialization = false

                initialize(
                    object : YouTubePlayerListener {
                        override fun onApiChange(youTubePlayer: YouTubePlayer) = Unit

                        override fun onCurrentSecond(
                            youTubePlayer: YouTubePlayer,
                            second: Float
                        ) = Unit

                        override fun onError(
                            youTubePlayer: YouTubePlayer,
                            error: PlayerConstants.PlayerError
                        ) = Unit

                        override fun onPlaybackQualityChange(
                            youTubePlayer: YouTubePlayer,
                            playbackQuality: PlayerConstants.PlaybackQuality
                        ) = Unit

                        override fun onPlaybackRateChange(
                            youTubePlayer: YouTubePlayer,
                            playbackRate: PlayerConstants.PlaybackRate
                        ) = Unit

                        override fun onReady(youTubePlayer: YouTubePlayer) {
                            youTubePlayer.loadVideo(
                                videoId = videoId,
                                0f
                            )
                            if (shouldStartMuted) {
                                youTubePlayer.mute()
                            }
                        }

                        override fun onStateChange(
                            youTubePlayer: YouTubePlayer,
                            state: PlayerConstants.PlayerState
                        ) = Unit

                        override fun onVideoDuration(
                            youTubePlayer: YouTubePlayer,
                            duration: Float
                        ) = Unit

                        override fun onVideoId(
                            youTubePlayer: YouTubePlayer,
                            videoId: String
                        ) = Unit

                        override fun onVideoLoadedFraction(
                            youTubePlayer: YouTubePlayer,
                            loadedFraction: Float
                        ) = Unit
                    },
                    IFramePlayerOptions
                        .Builder()
                        .controls(1)
                        .build()
                )
                layoutParams =
                    ViewGroup.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT
                    )
            }
        }

    youtubePlayer?.let { viewPlayer ->
        AndroidView(
            factory = { viewPlayer },
            modifier = modifier
        )
    }

    DisposableEffect(
        key1 = Unit,
        effect = {
            onDispose {
                youtubePlayer?.release()
                youtubePlayer = null
            }
        }
    )
}
