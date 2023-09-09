package com.gabrielbmoro.moviedb.repository.datasources.retrofit.responses

import com.google.gson.annotations.SerializedName

data class PageResponse(
    @SerializedName("page") val page: Int,
    @SerializedName("total_results") var totalResults: Int,
    @SerializedName("total_pages") var totalPages: Int,
    @SerializedName("results") var results: List<MovieResponse>? = null
) {

    companion object {
        fun mockPageWithWhyDoWeUseItMovieOnly() = PageResponse(
            results = listOf(
                MovieResponse(
                    id = 12L,
                    overview = "It is a long established fact that a reader will be distracted by the readable content of a page when looking at its layout. The point of using Lorem Ipsum is that it has a more-or-less normal distribution of letters, as opposed to using 'Content here, content here', making it look like readable English. Many desktop publishing packages and web page editors now use Lorem Ipsum as their default model text, and a search for 'lorem ipsum' will uncover many web sites still in their infancy. Various v",
                    title = "Why do we use it?",
                    originalTitle = "Why do we use it?",
                    releaseDate = "02-03-2020",
                    backdropPath = "https://image/bucket/s3/ga.png",
                    popularity = 10f,
                    votesAverage = 5f,
                    originalLanguage = "en-US",
                    isAdult = false,
                    votes = 2,
                    isVideo = false,
                    posterPath = "https://image/bucket/s3/ga.png"
                )
            ),
            totalResults = 150,
            page = 2,
            totalPages = 2
        )
    }
}