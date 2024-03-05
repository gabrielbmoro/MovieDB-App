package com.gabrielbmoro.moviedb.domain.usecases

import com.gabrielbmoro.moviedb.domain.MoviesRepository
import com.gabrielbmoro.moviedb.domain.entities.Movie
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
class GetFavoriteMoviesUseCaseImplTest {

    private lateinit var repository: MoviesRepository
    private lateinit var useCase: GetFavoriteMoviesUseCase

    @Before
    fun before() {
        repository = mockk()
        useCase = GetFavoriteMoviesUseCaseImpl(repository)
    }

    @Test
    fun `should be able to get all favorite movies`() = runTest {
        // arrange
        val favoriteMovies = listOf(Movie.mockWhiteDragonNotFavorite())

        coEvery { repository.getFavoriteMovies() }.returns(favoriteMovies)

        // act
        useCase.execute(Unit)

        // assert
        coVerify(exactly = 1) { repository.getFavoriteMovies() }
    }
}
