package com.gabrielbmoro.moviedb.domain.usecases

import com.gabrielbmoro.moviedb.domain.entities.MovieDetail
import com.gabrielbmoro.moviedb.domain.entities.VideoStream
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

@OptIn(ExperimentalCoroutinesApi::class)
class GetMovieDetailsUseCaseImplTest {
    private lateinit var repository: FakeRepository
    private lateinit var useCase: GetMovieDetailsUseCase

    private val validVideoStream =
        VideoStream(
            id = "0L",
            type = "Trailer",
            official = true,
            size = 12,
            site = "YouTube",
            name = "name",
            key = "a0s9d",
        )
    private val validStreamsList =
        listOf(
            validVideoStream,
            VideoStream(
                id = "1L",
                type = "Trailer",
                official = true,
                size = 12,
                site = "Twiter",
                name = "name",
                key = "a0s9d",
            ),
        )

    @BeforeTest
    fun before() {
        repository = FakeRepository()
        useCase = GetMovieDetailsUseCaseImpl(repository)
    }

    @Test
    fun `should be able to get the proper details about a movie`() =
        runTest {
            // arrange
            val expected =
                MovieDetail(
                    false,
                    0,
                    emptyList(),
                    "",
                    "",
                    "",
                    "",
                    emptyList(),
                    validVideoStream.key,
                    0f,
                    "",
                    "",
                    "",
                    "",
                    "",
                    "",
                    0f,
                )
            repository.movieDetails =
                MovieDetail(
                    false,
                    0,
                    emptyList(),
                    "",
                    "",
                    "",
                    "",
                    emptyList(),
                    null,
                    0f,
                    "",
                    "",
                    "",
                    "",
                    "",
                    "",
                    0f,
                )
            repository.videoStreams = validStreamsList

            // act
            val result = useCase.execute(GetMovieDetailsUseCase.Params(1))

            // assert
            assertEquals(expected, result)
        }
}
