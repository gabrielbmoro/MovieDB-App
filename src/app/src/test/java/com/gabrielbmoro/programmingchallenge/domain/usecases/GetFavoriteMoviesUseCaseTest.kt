package com.gabrielbmoro.programmingchallenge.domain.usecases

import com.gabrielbmoro.programmingchallenge.domain.usecases.GetFavoriteMoviesUseCase
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
class GetFavoriteMoviesUseCaseTest {

    private val repository: MoviesRepository = mockk()
    private val testDispatcher = TestCoroutineDispatcher()

    private fun getFavoriteMovieUseCaseTest() = GetFavoriteMoviesUseCase(repository)

    @Test
    fun `should be able to get all favorite movies`() {
        // arrange
        val favoriteUseCaseTest = getFavoriteMovieUseCaseTest()

        // act
        testDispatcher.runBlockingTest {
            favoriteUseCaseTest.execute()
        }

        // assert
        coVerify(exactly = 1) { repository.getFavoriteMovies() }
    }
}