package com.gabrielbmoro.programmingchallenge.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Movie(
    val votesAverage: Float,
    val title: String,
    val posterImageUrl: String,
    val backdropImageUrl: String,
    val overview: String,
    val releaseDate: String,
    var isFavorite: Boolean,
    val language: String,
    val popularity: Float
) : Parcelable