package com.gabrielbmoro.moviedb.domain.usecases

import com.gabrielbmoro.moviedb.domain.entities.Movie
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import kotlin.test.assertEquals

class GetFavoriteMoviesUseCaseImplTest {

    private lateinit var repository: FakeRepository
    private lateinit var useCase: GetFavoriteMoviesUseCase

    @Before
    fun before() {
        repository = FakeRepository()
        useCase = GetFavoriteMoviesUseCaseImpl(repository)
    }

    @Test
    fun `should be able to get all favorite movies`() = runTest {
        // arrange
        val favoriteMovies = listOf(Movie.mockWhiteDragonNotFavorite())

        repository.favoriteMovies = favoriteMovies

        // act
        val result = useCase.execute(Unit)

        // assert
        assertEquals(favoriteMovies, result)
    }
}
