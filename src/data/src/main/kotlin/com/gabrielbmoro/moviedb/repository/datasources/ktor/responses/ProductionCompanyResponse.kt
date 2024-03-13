package com.gabrielbmoro.moviedb.repository.datasources.ktor.responses

import kotlinx.serialization.Serializable

@Suppress("PropertyName")
@Serializable
data class ProductionCompanyResponse(
    val id: Long,
    val logo_path: String?,
    val name: String,
    val origin_country: String
)
