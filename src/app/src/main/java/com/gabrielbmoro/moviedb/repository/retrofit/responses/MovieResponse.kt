package com.gabrielbmoro.moviedb.repository.retrofit.responses

import com.google.gson.annotations.SerializedName

data class MovieResponse(
    @SerializedName("id") val id: Long,
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
) {

    companion object {
        fun mockWhyDoWeUseMovieResponse() = MovieResponse(
            id = 12L,
            overview = "It is a long established fact that a reader will be distracted by the readable content of a page when looking at its layout. The point of using Lorem Ipsum is that it has a more-or-less normal distribution of letters, as opposed to using 'Content here, content here', making it look like readable English. Many desktop publishing packages and web page editors now use Lorem Ipsum as their default model text, and a search for 'lorem ipsum' will uncover many web sites still in their infancy. Various v",
            title = "Why do we use it?",
            originalTitle = "Why do we use it?",
            releaseDate = "02-03-2020",
            backdropPath = "/ga.png",
            popularity = 10f,
            votesAverage = 5f,
            originalLanguage = "en-US",
            isAdult = false,
            votes = 2,
            isVideo = false,
            posterPath = "/ga.png"
        )
    }
}