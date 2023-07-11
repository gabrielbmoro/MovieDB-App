package com.gabrielbmoro.moviedb.ui.common.widgets

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.PlayerConstants
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.YouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView

@Composable
fun VideoPlayer(videoId: String, modifier : Modifier = Modifier) {
    AndroidView(
        factory = {
            YouTubePlayerView(it).apply {
                addYouTubePlayerListener(
                    object : YouTubePlayerListener {
                        override fun onApiChange(youTubePlayer: YouTubePlayer) { }

                        override fun onCurrentSecond(
                            youTubePlayer: YouTubePlayer,
                            second: Float
                        ) { }

                        override fun onError(
                            youTubePlayer: YouTubePlayer,
                            error: PlayerConstants.PlayerError
                        ) { }

                        override fun onPlaybackQualityChange(
                            youTubePlayer: YouTubePlayer,
                            playbackQuality: PlayerConstants.PlaybackQuality
                        ) { }

                        override fun onPlaybackRateChange(
                            youTubePlayer: YouTubePlayer,
                            playbackRate: PlayerConstants.PlaybackRate
                        ) { }

                        override fun onReady(youTubePlayer: YouTubePlayer) {
                            youTubePlayer.loadVideo(
                                videoId = videoId,
                                0f
                            )
                        }

                        override fun onStateChange(
                            youTubePlayer: YouTubePlayer,
                            state: PlayerConstants.PlayerState
                        ) { }

                        override fun onVideoDuration(
                            youTubePlayer: YouTubePlayer,
                            duration: Float
                        ) { }

                        override fun onVideoId(
                            youTubePlayer: YouTubePlayer,
                            videoId: String
                        ) { }

                        override fun onVideoLoadedFraction(
                            youTubePlayer: YouTubePlayer,
                            loadedFraction: Float
                        ) { }
                    }
                )
            }
        },
        modifier = modifier
    )
}