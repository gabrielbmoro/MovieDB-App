package com.gabrielbmoro.moviedb.data.repository.datasources.ktor.responses

import kotlinx.serialization.Serializable

@Suppress("PropertyName", "ConstructorParameterNaming")
@Serializable
data class TvShowDetailResponse(
    val id: Long,
    val name: String?,
    val vote_average: Float?,
    val poster_path: String?,
    val backdrop_path: String?,
    val overview: String?,
    val first_air_date: String?,
    val last_air_date: String?,
    val original_language: String?,
    val popularity: Float?,
    val status: String?,
    val tagline: String?,
    val homepage: String?,
    val genres: List<GenreResponse>?,
    val networks: List<NetworkResponse>?,
    val created_by: List<CreatedByResponse>?,
    val production_companies: List<ProductionCompanyResponse>?,
    val number_of_seasons: Int?,
    val number_of_episodes: Int?,
)

@Suppress("PropertyName", "ConstructorParameterNaming")
@Serializable
data class NetworkResponse(
    val id: Int,
    val name: String,
    val logo_path: String?,
    val origin_country: String,
)

@Suppress("PropertyName", "ConstructorParameterNaming")
@Serializable
data class CreatedByResponse(
    val id: Int,
    val name: String,
    val credit_id: String?,
)
