package com.gabrielbmoro.moviedb.repository.datasources.retrofit.responses

import com.google.gson.annotations.SerializedName

data class VideoStreamsResponseItem(
    @SerializedName("id")
    val id: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("key")
    val key: String,
    @SerializedName("site")
    val site: String,
    @SerializedName("size")
    val size: Int,
    @SerializedName("official")
    val official: Boolean,
    @SerializedName("type")
    val type: String,
)

data class VideoStreamsResponse(
    @SerializedName("results")
    val results: List<VideoStreamsResponseItem>
)