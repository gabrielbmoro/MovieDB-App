package com.gabrielbmoro.programmingchallenge.usecases.mapper

import com.gabrielbmoro.programmingchallenge.repository.entities.Movie
import com.gabrielbmoro.programmingchallenge.repository.retrofit.responses.MovieResponse
import com.gabrielbmoro.programmingchallenge.repository.retrofit.responses.PageResponse
import com.gabrielbmoro.programmingchallenge.usecases.mappers.MovieMapper
import com.gabrielbmoro.programmingchallenge.usecases.mappers.PageMapper
import com.google.common.truth.Truth
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@ExperimentalCoroutinesApi
@RunWith(JUnit4::class)
class PageMapperTest {

    private val movieMapper: MovieMapper = mockk()

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

        every { movieMapper.mapResponse(any()) }.answers {
            Movie(
                isFavorite = true,
                overview = "It is a long established fact that a reader will be distracted by the readable content of a page when looking at its layout. The point of using Lorem Ipsum is that it has a more-or-less normal distribution of letters, as opposed to using 'Content here, content here', making it look like readable English. Many desktop publishing packages and web page editors now use Lorem Ipsum as their default model text, and a search for 'lorem ipsum' will uncover many web sites still in their infancy. Various v",
                title = "Why do we use it?",
                releaseDate = "02-03-2020",
                imageUrl = "https://image/bucket/s3/ga.png",
                popularity = 10f,
                votesAverage = 5f,
                language = "en-US"
            )
        }

        // act
        val result = PageMapper(movieMapper).map(pageResponse)

        verify { movieMapper.mapResponse(any()) }

        // assert
        Truth.assertThat(result.totalPages).isEqualTo(pageResponse.totalPages)
        Truth.assertThat(result.pageNumber).isEqualTo(pageResponse.page)
    }
}