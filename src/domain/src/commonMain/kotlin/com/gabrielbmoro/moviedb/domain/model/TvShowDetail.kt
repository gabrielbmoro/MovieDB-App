package com.gabrielbmoro.moviedb.domain.model

data class TvShowDetail(
    val id: Long,
    val name: String,
    val votesAverage: Float,
    val posterImageUrl: String?,
    val backdropImageUrl: String?,
    val overview: String,
    val firstAirDate: String,
    val lastAirDate: String,
    val originalLanguage: String,
    val popularity: Float,
    val status: String,
    val tagline: String?,
    val homepage: String?,
    val genres: List<String>,
    val networks: List<String>,
    val createdBy: List<String>,
    val productionCompanies: List<String>,
    val numberOfSeasons: Int,
    val numberOfEpisodes: Int,
    val videoId: String? = null,
)
