package com.gabrielbmoro.moviedb.repository.datasources.ktor.responses

import kotlinx.serialization.Serializable

@Serializable
data class GenreResponse(
    val id: Int,
    val name: String
)
