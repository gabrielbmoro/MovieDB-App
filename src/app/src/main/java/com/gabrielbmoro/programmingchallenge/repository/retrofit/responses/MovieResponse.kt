package com.gabrielbmoro.programmingchallenge.repository.retrofit.responses

import com.google.gson.annotations.SerializedName

data class MovieResponse(
    @SerializedName("vote_count") val votes: Int?,
    @SerializedName("video") val isVideo: Boolean?,
    @SerializedName("vote_average") val votesAverage: Float?,
    @SerializedName("title") val title: String?,
    @SerializedName("popularity") val popularity: Float?,
    @SerializedName("poster_path") val posterPath: String?,
    @SerializedName("original_language") val originalLanguage: String?,
    @SerializedName("original_title") val originalTitle: String?,
    @SerializedName("backdrop_path") val backdropPath: String?,
    @SerializedName("adult") val isAdult: Boolean?,
    @SerializedName("overview") val overview: String?,
    @SerializedName("release_date") val releaseDate: String?,
)