package com.gabrielbmoro.moviedb.repository.datasources.retrofit.responses

import com.google.gson.annotations.SerializedName

data class MovieDetailResponse(
    @SerializedName("adult")
    val adult: Boolean,
    @SerializedName("budget")
    val budget: Int,
    @SerializedName("genres")
    val genres: List<GenreResponse>,
    @SerializedName("homepage")
    val homepage: String,
    @SerializedName("imdb_id")
    val imdbId: String,
    @SerializedName("status")
    val status: String,
    @SerializedName("tagline")
    val tagline: String,
    @SerializedName("production_companies")
    val productionCompanies: List<ProductionCompanyResponse>
)
