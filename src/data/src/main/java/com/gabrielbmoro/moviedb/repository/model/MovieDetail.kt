package com.gabrielbmoro.moviedb.repository.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class MovieDetail(
    val adult: Boolean,
    val backdropPath: String,
    val budget: Int,
    val genres: String,
    val homepage: String,
    val id: Long,
    val imdbId: String,
    val originalLanguage: String,
    val originalTitle: String,
    val overview: String,
    val popularity: Number,
    val posterPath: String,
    var videoId: String? = null,
) : Parcelable