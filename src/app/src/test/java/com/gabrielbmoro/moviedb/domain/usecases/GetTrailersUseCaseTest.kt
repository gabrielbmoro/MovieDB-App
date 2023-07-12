package com.gabrielbmoro.moviedb.domain.usecases

import com.gabrielbmoro.moviedb.domain.model.DataOrException
import com.gabrielbmoro.moviedb.domain.model.VideoStream
import com.gabrielbmoro.moviedb.repository.MoviesRepository
import com.google.common.truth.Truth
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
class GetTrailersUseCaseTest {

    private lateinit var repository: MoviesRepository
    private lateinit var useCase: GetTrailersUseCase

    @Before
    fun before() {
        repository = mockk()
        useCase = GetTrailersUseCase(repository)
    }

    @Test
    fun `should be able to get a valid trailer - existing valid trailer`() {
        // arrange
        val movieId = 123L
        coEvery { repository.getVideoStreams(movieId) }.returns(
            DataOrException(
                listOf(
                    VideoStream.mockVideoStreamNotTrailer(),
                    VideoStream.mockVideoStreamTrailer(),
                    VideoStream.mockVideoStreamNotYouTube()
                )
            )
        )

        runTest {
            // act
            val result = useCase(movieId)

            // assert
            Truth.assertThat(result.data).isEqualTo(VideoStream.mockVideoStreamTrailer())
        }
    }

    @Test
    fun `should not be able to get a valid trailer - no valid youtube trailer`() {
        // arrange
        val movieId = 123L
        coEvery { repository.getVideoStreams(movieId) }.returns(
            DataOrException(
                listOf(
                    VideoStream.mockVideoStreamNotYouTube()
                )
            )
        )

        runTest {
            // act
            val result = useCase(movieId)

            // assert
            Truth.assertThat(result.data).isNull()
        }
    }

    @Test
    fun `should not be able to get a valid trailer - no valid trailer`() {
        // arrange
        val movieId = 123L
        coEvery { repository.getVideoStreams(movieId) }.returns(
            DataOrException(
                listOf(
                    VideoStream.mockVideoStreamNotTrailer(),
                )
            )
        )

        runTest {
            // act
            val result = useCase(movieId)

            // assert
            Truth.assertThat(result.data).isNull()
        }
    }

    @Test
    fun `should be able to get a valid trailer - passing one time by repository`() {
        // arrange
        val movieId = 123L
        coEvery { repository.getVideoStreams(movieId) }.returns(
            DataOrException(
                listOf(
                    VideoStream.mockVideoStreamNotTrailer(),
                    VideoStream.mockVideoStreamTrailer(),
                    VideoStream.mockVideoStreamNotYouTube()
                )
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