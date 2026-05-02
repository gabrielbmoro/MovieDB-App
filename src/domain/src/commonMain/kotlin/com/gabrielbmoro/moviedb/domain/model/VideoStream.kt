package com.gabrielbmoro.moviedb.domain.model

data class VideoStream(
    val id: String,
    val name: String,
    val key: String,
    val site: String,
    val size: Int,
    val official: Boolean,
    val type: String,
)
