package com.gabrielbmoro.programmingchallenge.domain.usecases

import com.gabrielbmoro.programmingchallenge.domain.usecases.GetTopRatedMoviesUseCase
import com.gabrielbmoro.programmingchallenge.repository.MoviesRepository
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@ExperimentalCoroutinesApi
@RunWith(JUnit4::class)
class GetTopRatedMoviesUseCaseTest {

    private val repository: MoviesRepository = mockk()
    private val testDispatcher = TestCoroutineDispatcher()

    private fun getTopRatedMovieUseCaseTest() = GetTopRatedMoviesUseCase(repository)

    @Test
    fun `should be able to get popular movies`() {
        // arrange
        val topRatedUseCaseTest = getTopRatedMovieUseCaseTest()

        // act
        testDispatcher.runBlockingTest {
            topRatedUseCaseTest.execute(1)
        }

        // assert
        coVerify(exactly = 1){ repository.getTopRatedMovies(1) }
    }
}