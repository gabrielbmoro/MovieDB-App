package com.gabrielbmoro.moviedb.domain.usecases

import com.gabrielbmoro.moviedb.domain.model.DataOrException
import com.gabrielbmoro.moviedb.domain.model.VideoStream
import com.gabrielbmoro.moviedb.repository.MoviesRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@ExperimentalCoroutinesApi
@RunWith(JUnit4::class)
class GetVideoStreamUseCaseTest {

    private lateinit var repository: MoviesRepository
    private lateinit var useCase: GetVideoStreamUseCase

    @Before
    fun before() {
        repository = mockk()
        useCase = GetVideoStreamUseCase(repository)
    }

    @Test
    fun `should be able to get all video streams available to a specific movie`() {
        // arrange
        val movieId = 123L
        coEvery { repository.getVideoStreams(movieId) }.returns(
            DataOrException(
                listOf(VideoStream.mockVideoStream())
            )
        )

        runTest {
            // act
            useCase(movieId)

            // assert
            coVerify(exactly = 1) { repository.getVideoStreams(movieId) }
        }
    }
}