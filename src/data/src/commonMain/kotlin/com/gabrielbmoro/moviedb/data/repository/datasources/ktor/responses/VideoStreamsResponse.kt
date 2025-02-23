package com.gabrielbmoro.moviedb.data.repository.datasources.ktor.responses

import kotlinx.serialization.Serializable

@Serializable
data class VideoStreamsResponseItem(
    val id: String,
    val name: String,
    val key: String,
    val site: String,
    val size: Int,
    val official: Boolean,
    val type: String,
)

@Serializable
data class VideoStreamsResponse(
    val results: List<VideoStreamsResponseItem>,
)
