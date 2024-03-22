package com.gabrielbmoro.moviedb.domain.usecases

import kotlinx.coroutines.test.runTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

class IsFavoriteMovieUseCaseImplTest {

    private lateinit var repository: FakeRepository
    private lateinit var useCase: IsFavoriteMovieUseCase

    @BeforeTest
    fun before() {
        repository = FakeRepository()
        useCase = IsFavoriteMovieUseCaseImpl(repository)
    }

    @Test
    fun `should be able to check if a movie is favorite`() = runTest {
        // arrange
        val movieTitle = "Dragão branco"

        repository.isFavoriteMovie = true

        // act
        useCase.execute(IsFavoriteMovieUseCase.Params(movieTitle = movieTitle))

        // assert
        assertEquals(1, repository.timesCallCheckFavorite)
    }
}
