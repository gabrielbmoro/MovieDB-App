package com.gabrielbmoro.moviedb.repository.datasources.retrofit.responses

import com.google.gson.annotations.SerializedName

data class GenreResponse(
    @SerializedName("id")
    val id: String,
    @SerializedName("name")
    val name: String
)
