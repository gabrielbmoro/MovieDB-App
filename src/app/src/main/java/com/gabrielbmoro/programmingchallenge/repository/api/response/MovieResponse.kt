package com.gabrielbmoro.programmingchallenge.repository.api.response

import com.google.gson.annotations.SerializedName

data class MovieResponse(
        @SerializedName("vote_count") val votes: Int?,
        @SerializedName("video") var isVideo: Boolean?,
        @SerializedName("vote_average") var votesAverage: Float?,
        @SerializedName("title") var title: String?,
        @SerializedName("popularity") var popularity: Float?,
        @SerializedName("poster_path") var posterPath: String?,
        @SerializedName("original_language") var originalLanguage: String?,
        @SerializedName("original_title") var originalTitle: String?,
        @SerializedName("backdrop_path") var backdropPath: String?,
        @SerializedName("adult") var isAdult: Boolean?,
        @SerializedName("overview") var overview: String?,
        @SerializedName("release_date") var releaseDate: String?
)