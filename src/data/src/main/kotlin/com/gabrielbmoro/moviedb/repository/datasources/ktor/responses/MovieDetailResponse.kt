package com.gabrielbmoro.moviedb.repository.datasources.ktor.responses

import kotlinx.serialization.Serializable

@Serializable
data class MovieDetailResponse(
    val adult: Boolean,
    val budget: Int,
    val genres: List<GenreResponse>,
    val homepage: String,
    val imdb_id: String,
    val status: String,
    val tagline: String,
    val production_companies: List<ProductionCompanyResponse>
)
