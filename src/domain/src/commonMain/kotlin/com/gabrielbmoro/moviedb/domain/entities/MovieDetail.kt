package com.gabrielbmoro.moviedb.domain.entities

data class MovieDetail(
    val adult: Boolean,
    val budget: Int,
    val genres: List<String>,
    val homepage: String,
    val imdbId: String,
    val status: String,
    val tagline: String,
    val productionCompanies: List<String>,
    var videoId: String? = null,
    val votesAverage: Float,
    val title: String,
    val posterImageUrl: String?,
    val backdropImageUrl: String?,
    val overview: String,
    val releaseDate: String,
    val language: String,
    val popularity: Float
)
