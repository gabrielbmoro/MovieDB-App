package com.gabrielbmoro.moviedb.repository.datasources.ktor.responses

import kotlinx.serialization.Serializable

@Serializable
data class PageResponse(
    val page: Int,
    var total_results: Int,
    var total_pages: Int,
    var results: List<MovieResponse>? = null
) {

    companion object {
        fun mockPageWithWhyDoWeUseItMovieOnly() = PageResponse(
            results = listOf(
                MovieResponse(
                    id = 12L,
                    overview = "It is a long established fact that a reader will be distracted by the readable content of a page when looking at its layout. The point of using Lorem Ipsum is that it has a more-or-less normal distribution of letters, as opposed to using 'Content here, content here', making it look like readable English. Many desktop publishing packages and web page editors now use Lorem Ipsum as their default model text, and a search for 'lorem ipsum' will uncover many web sites still in their infancy. Various v",
                    title = "Why do we use it?",
                    original_title = "Why do we use it?",
                    release_date = "02-03-2020",
                    backdrop_path = "https://image/bucket/s3/ga.png",
                    popularity = 10f,
                    vote_average = 5f,
                    original_language = "en-US",
                    adult = false,
                    vote_count = 2,
                    video = false,
                    poster_path = "https://image/bucket/s3/ga.png"
                )
            ),
            total_results = 150,
            page = 2,
            total_pages = 2
        )
    }
}
