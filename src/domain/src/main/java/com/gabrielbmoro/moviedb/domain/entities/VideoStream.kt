package com.gabrielbmoro.moviedb.domain.entities

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class VideoStream(
    val id: String,
    val name: String,
    val key: String,
    val site: String,
    val size: Int,
    val official: Boolean,
    val type: String
) : Parcelable {

    companion object {
        fun mockVideoStreamNotTrailer() = VideoStream(
            id = "12341",
            name = "Chuck Norris - the battle",
            key = "lViasfV1",
            site = "YouTube",
            official = true,
            type = "Not trailer",
            size = 1024
        )

        fun mockVideoStreamTrailer() = VideoStream(
            id = "12341",
            name = "Chuck Norris - the battle",
            key = "lViasfV1",
            site = "YouTube",
            official = true,
            type = "Trailer",
            size = 1024
        )

        fun mockVideoStreamNotYouTube() = VideoStream(
            id = "12341",
            name = "Chuck Norris - the battle",
            key = "lViasfV1",
            site = "Vimeo",
            official = true,
            type = "Trailer",
            size = 1024
        )
    }
}