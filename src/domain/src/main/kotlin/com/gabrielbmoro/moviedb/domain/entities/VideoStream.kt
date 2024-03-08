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
) : Parcelable