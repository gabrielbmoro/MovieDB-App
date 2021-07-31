package com.gabrielbmoro.programmingchallenge.repository.retrofit.responses

import com.google.gson.annotations.SerializedName

data class PageResponse(
    @SerializedName("page") val page: Int,
    @SerializedName("total_results") var totalResults: Int,
    @SerializedName("total_pages") var totalPages: Int,
    @SerializedName("results") var results: List<MovieResponse>? = null
)