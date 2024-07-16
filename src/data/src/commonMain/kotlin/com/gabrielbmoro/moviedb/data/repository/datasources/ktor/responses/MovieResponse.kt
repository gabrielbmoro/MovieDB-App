package com.gabrielbmoro.moviedb.data.repository.datasources.ktor.responses

import kotlinx.serialization.Serializable

@Suppress("PropertyName", "ConstructorParameterNaming")
@Serializable
data class MovieResponse(
    val id: Long,
    val vote_count: Int?,
    val video: Boolean?,
    val vote_average: Float?,
    val title: String?,
    val popularity: Float?,
    val poster_path: String?,
    val original_language: String?,
    val original_title: String?,
    val backdrop_path: String?,
    val adult: Boolean?,
    val overview: String?,
    val release_date: String?
)
