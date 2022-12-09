package com.gabrielbmoro.programmingchallenge.repository.entities

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Movie(
    val votesAverage: Float,
    val title: String,
    val imageUrl: String,
    val overview: String,
    val releaseDate: String,
    var isFavorite: Boolean,
    val language: String,
    val popularity : Float
) : Parcelable