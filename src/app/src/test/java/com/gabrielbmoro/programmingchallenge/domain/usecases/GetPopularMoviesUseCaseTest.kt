package com.gabrielbmoro.programmingchallenge.domain.usecases

import com.gabrielbmoro.programmingchallenge.domain.usecases.GetPopularMoviesUseCase
import com.gabrielbmoro.programmingchallenge.repository.MoviesRepository
import com.gabrielbmoro.programmingchallenge.domain.model.Page
import com.gabrielbmoro.programmingchallenge.repository.retrofit.responses.PageResponse
import com.gabrielbmoro.programmingchallenge.repository.mappers.PageMapper
import io.mockk.coEvery
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
class GetPopularMoviesUseCaseTest {

    private val repository: MoviesRepository = mockk()
    private val testDispatcher = TestCoroutineDispatcher()

    private fun getPopularMovieUseCaseTest() = GetPopularMoviesUseCase(repository)

    @Test
    fun `should be able to get popular movies`() {
        // arrange
        val popularUseCaseTest = getPopularMovieUseCaseTest()

        // act
        testDispatcher.runBlockingTest {
            popularUseCaseTest.execute(1)
        }

        // assert
        coVerify(exactly = 1){ repository.getPopularMovies(1) }
    }
}