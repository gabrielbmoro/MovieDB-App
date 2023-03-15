package com.gabrielbmoro.programmingchallenge.repository.mapper

import com.gabrielbmoro.programmingchallenge.repository.retrofit.responses.MovieResponse
import com.gabrielbmoro.programmingchallenge.repository.retrofit.responses.PageResponse
import com.gabrielbmoro.programmingchallenge.repository.mappers.MovieMapper
import com.gabrielbmoro.programmingchallenge.repository.mappers.PageMapper
import com.google.common.truth.Truth
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@ExperimentalCoroutinesApi
@RunWith(JUnit4::class)
class PageMapperTest {

    lateinit var movieMapper: MovieMapper

    @Before
    fun before() {
        movieMapper = MovieMapper()
    }

    @Test
    fun `should be able to convert page response to page object`() {
        // arrange
        val pageResponse = PageResponse(
            results = listOf(
                MovieResponse(
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

        // act
        val result = PageMapper(movieMapper).map(pageResponse)

        // assert
        Truth.assertThat(result.totalPages).isEqualTo(pageResponse.totalPages)
        Truth.assertThat(result.pageNumber).isEqualTo(pageResponse.page)
    }

    @Test
    fun `should be able to consider just valid movies`() {
        // arrange
        val pageResponse = PageResponse(
            results = listOf(
                // invalid
                MovieResponse(
                    overview = "It is a long established fact that a reader will be distracted by the readable content of a page when looking at its layout. The point of using Lorem Ipsum is that it has a more-or-less normal distribution of letters, as opposed to using 'Content here, content here', making it look like readable English. Many desktop publishing packages and web page editors now use Lorem Ipsum as their default model text, and a search for 'lorem ipsum' will uncover many web sites still in their infancy. Various v",
                    title = "Why do we use it?",
                    originalTitle = "Why do we use it?",
                    releaseDate = "02-03-2020",
                    backdropPath = "",
                    popularity = 10f,
                    votesAverage = 5f,
                    originalLanguage = "en-US",
                    isAdult = false,
                    votes = 2,
                    isVideo = false,
                    posterPath = "https://image/bucket/s3/ga.png"
                ),
                // invalid
                MovieResponse(
                    overview = "",
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
                ),
                // valid
                MovieResponse(
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

        // act
        val result = PageMapper(movieMapper).map(pageResponse)

        // assert
        Truth.assertThat(result.movies.size).isEqualTo(1)
        Truth.assertThat(result.movies.first().isValid).isTrue()
    }
}