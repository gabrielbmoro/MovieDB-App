package com.gabrielbmoro.programmingchallenge.repository.entities

import com.google.gson.annotations.SerializedName

data class PageMovies(
        @SerializedName("page") val page: Int,
        @SerializedName("total_results") var totalResults: Int,
        @SerializedName("total_pages") var totalPages: Int,
        @SerializedName("results") var results: List<Movie>? = null
)