package com.gabrielbmoro.moviedb.repository.datasources.retrofit.responses

import com.google.gson.annotations.SerializedName

data class MovieDetailResponse(
    @SerializedName("adult")
    val adult: Boolean,
    @SerializedName("backdrop_path")
    val backdropPath: String,
    @SerializedName("belongs_to_collection")
    val belongsToCollection: Any?,
    @SerializedName("budget")
    val budget: Int,
    @SerializedName("genres")
    val genres: List<GenreResponse>,
    @SerializedName("homepage")
    val homepage: String,
    @SerializedName("id")
    val id: Long,
    @SerializedName("imdb_id")
    val imdbId: String,
    @SerializedName("original_language")
    val originalLanguage: String,
    @SerializedName("original_title")
    val originalTitle: String,
    @SerializedName("overview")
    val overview: String,
    @SerializedName("popularity")
    val popularity: Number,
    @SerializedName("poster_path")
    val posterPath: String,
)