package com.gabrielbmoro.moviedb.domain.usecases

import com.gabrielbmoro.moviedb.domain.entities.Movie
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

@OptIn(ExperimentalCoroutinesApi::class)
class GetFavoriteMoviesUseCaseImplTest {

    private lateinit var repository: FakeRepository
    private lateinit var useCase: GetFavoriteMoviesUseCase

    @BeforeTest
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
