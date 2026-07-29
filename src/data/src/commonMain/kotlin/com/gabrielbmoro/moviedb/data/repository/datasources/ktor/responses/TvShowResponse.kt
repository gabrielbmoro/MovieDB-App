package com.gabrielbmoro.moviedb.data.repository.datasources.ktor.responses

import kotlinx.serialization.Serializable

@Suppress("PropertyName", "ConstructorParameterNaming")
@Serializable
data class TvShowResponse(
    val id: Long,
    val name: String?,
    val original_name: String?,
    val vote_average: Float?,
    val poster_path: String?,
    val backdrop_path: String?,
    val overview: String?,
    val first_air_date: String?,
    val original_language: String?,
    val popularity: Float?,
    val vote_count: Int?,
    val origin_country: List<String>?,
)
