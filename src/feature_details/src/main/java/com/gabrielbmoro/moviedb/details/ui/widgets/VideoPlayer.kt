package com.gabrielbmoro.moviedb.details.ui.widgets

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
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView

@Composable
fun VideoPlayer(videoId: String, modifier: Modifier = Modifier) {

    val context = LocalContext.current

    var youtubePlayer: YouTubePlayerView? = remember {
        YouTubePlayerView(
            context
        ).apply {
            layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            )

            addYouTubePlayerListener(
                object : YouTubePlayerListener {
                    override fun onApiChange(youTubePlayer: YouTubePlayer) {}

                    override fun onCurrentSecond(
                        youTubePlayer: YouTubePlayer,
                        second: Float
                    ) {
                    }

                    override fun onError(
                        youTubePlayer: YouTubePlayer,
                        error: PlayerConstants.PlayerError
                    ) {
                    }

                    override fun onPlaybackQualityChange(
                        youTubePlayer: YouTubePlayer,
                        playbackQuality: PlayerConstants.PlaybackQuality
                    ) {
                    }

                    override fun onPlaybackRateChange(
                        youTubePlayer: YouTubePlayer,
                        playbackRate: PlayerConstants.PlaybackRate
                    ) {
                    }

                    override fun onReady(youTubePlayer: YouTubePlayer) {
                        youTubePlayer.loadVideo(
                            videoId = videoId,
                            0f
                        )
                        youTubePlayer.mute()
                    }

                    override fun onStateChange(
                        youTubePlayer: YouTubePlayer,
                        state: PlayerConstants.PlayerState
                    ) {
                    }

                    override fun onVideoDuration(
                        youTubePlayer: YouTubePlayer,
                        duration: Float
                    ) {
                    }

                    override fun onVideoId(
                        youTubePlayer: YouTubePlayer,
                        videoId: String
                    ) {
                    }

                    override fun onVideoLoadedFraction(
                        youTubePlayer: YouTubePlayer,
                        loadedFraction: Float
                    ) {
                    }
                }
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