package com.gabrielbmoro.moviedb.domain.usecases

import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import kotlin.test.assertEquals

class GetPopularMoviesUseCaseImplTest {

    private lateinit var repository: FakeRepository
    private lateinit var useCase: GetPopularMoviesUseCase

    @Before
    fun before() {
        repository = FakeRepository()
        useCase = GetPopularMoviesUseCaseImpl(repository)
    }

    @Test
    fun `should be able to get all popular movies`() = runTest {
        // arrange
        repository.popularMovies = emptyList()

        // act
        val result = useCase.execute(GetPopularMoviesUseCase.Params(1))

        // assert
        assertEquals(emptyList(), result)
    }
}
