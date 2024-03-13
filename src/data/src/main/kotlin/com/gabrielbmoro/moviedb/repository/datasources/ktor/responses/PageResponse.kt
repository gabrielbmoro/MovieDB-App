
package com.gabrielbmoro.moviedb.repository.datasources.ktor.responses

import kotlinx.serialization.Serializable

@Suppress("PropertyName")
@Serializable
data class PageResponse(
    val page: Int,
    var total_results: Int,
    var total_pages: Int,
    var results: List<MovieResponse>? = null
)
