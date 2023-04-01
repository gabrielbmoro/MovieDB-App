package com.gabrielbmoro.programmingchallenge.domain.usecases

import com.gabrielbmoro.programmingchallenge.domain.model.DataOrException
import com.gabrielbmoro.programmingchallenge.domain.model.Movie
import com.gabrielbmoro.programmingchallenge.repository.MoviesRepository
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
class GetFavoriteMoviesUseCaseTest {

    private lateinit var repository: MoviesRepository
    private lateinit var useCase: GetFavoriteMoviesUseCase

    @Before
    fun before() {
        repository = mockk()
        useCase = GetFavoriteMoviesUseCase(repository)
    }

    @Test
    fun `should be able to get all favorite movies`() {
        // arrange
        val favoriteMovies = listOf(Movie.mockWhiteDragonNotFavorite())

        coEvery { repository.getFavoriteMovies() }.returns(DataOrException(favoriteMovies))

        runTest {
            // act
            useCase()

            // assert
            coVerify(exactly = 1) { repository.getFavoriteMovies() }
        }
    }
}