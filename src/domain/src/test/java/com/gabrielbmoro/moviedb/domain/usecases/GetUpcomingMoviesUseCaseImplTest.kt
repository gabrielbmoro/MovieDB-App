package com.gabrielbmoro.moviedb.domain.usecases

import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import kotlin.test.assertEquals

class GetUpcomingMoviesUseCaseImplTest {
    private lateinit var repository: FakeRepository
    private lateinit var useCase: GetUpcomingMoviesUseCase

    @Before
    fun before() {
        repository = FakeRepository()
        useCase = GetUpcomingMoviesUseCaseImpl(repository)
    }

    @Test
    fun `should be able to get all up coming movies`() = runTest {
        // arrange
        repository.upComingMovies = emptyList()

        // act
        val result = useCase.execute(GetUpcomingMoviesUseCase.Params(1))

        // assert
        assertEquals(emptyList(), result)
    }
}