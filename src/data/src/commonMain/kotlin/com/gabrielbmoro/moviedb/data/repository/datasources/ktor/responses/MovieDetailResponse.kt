package com.gabrielbmoro.moviedb.data.repository.datasources.ktor.responses

import kotlinx.serialization.Serializable

@Suppress("PropertyName")
@Serializable
data class MovieDetailResponse(
    val adult: Boolean,
    val budget: Int,
    val genres: List<GenreResponse>,
    val homepage: String,
    val imdb_id: String,
    val status: String,
    val tagline: String,
    val production_companies: List<ProductionCompanyResponse>,
    val vote_count: Int?,
    val video: Boolean?,
    val vote_average: Float?,
    val title: String?,
    val popularity: Float?,
    val poster_path: String?,
    val original_language: String?,
    val original_title: String?,
    val backdrop_path: String?,
    val overview: String?,
    val release_date: String?,
)
