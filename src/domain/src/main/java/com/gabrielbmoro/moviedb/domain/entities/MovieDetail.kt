package com.gabrielbmoro.moviedb.domain.entities

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class MovieDetail(
    val adult: Boolean,
    val budget: Int,
    val genres: List<String>,
    val homepage: String,
    val imdbId: String,
    val status: String,
    val tagline: String,
    val productionCompanies: List<String>,
    var videoId: String? = null
) : Parcelable