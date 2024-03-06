package com.gabrielbmoro.moviedb.domain.usecases

import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import kotlin.test.assertEquals

class GetTopRatedMoviesUseCaseTest {

    private lateinit var repository: FakeRepository
    private lateinit var useCase: GetTopRatedMoviesUseCase

    @Before
    fun before() {
        repository = FakeRepository()
        useCase = GetTopRatedMoviesUseCaseImpl(repository)
    }

    @Test
    fun `should be able to get all top rated movies`() = runTest {
        // arrange
        repository.topRatedMovies = emptyList()

        // act
        val result = useCase.execute(GetTopRatedMoviesUseCase.Params(1))

        // assert
        assertEquals(emptyList(), result)
    }
}
