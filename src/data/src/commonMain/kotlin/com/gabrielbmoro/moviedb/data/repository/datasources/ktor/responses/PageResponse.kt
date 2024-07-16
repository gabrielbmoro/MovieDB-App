package com.gabrielbmoro.moviedb.data.repository.datasources.ktor.responses

import kotlinx.serialization.Serializable

@Suppress("PropertyName", "ConstructorParameterNaming")
@Serializable
data class PageResponse(
    val page: Int,
    var total_results: Int,
    var total_pages: Int,
    var results: List<MovieResponse>? = null
)
