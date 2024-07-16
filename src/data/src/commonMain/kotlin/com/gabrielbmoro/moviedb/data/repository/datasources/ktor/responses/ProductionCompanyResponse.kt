package com.gabrielbmoro.moviedb.data.repository.datasources.ktor.responses

import kotlinx.serialization.Serializable

@Suppress("PropertyName", "ConstructorParameterNaming")
@Serializable
data class ProductionCompanyResponse(
    val id: Long,
    val logo_path: String?,
    val name: String,
    val origin_country: String
)
