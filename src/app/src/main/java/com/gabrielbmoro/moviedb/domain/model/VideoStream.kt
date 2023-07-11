package com.gabrielbmoro.moviedb.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class VideoStream(
    val key: String,
    val site: String
) : Parcelable