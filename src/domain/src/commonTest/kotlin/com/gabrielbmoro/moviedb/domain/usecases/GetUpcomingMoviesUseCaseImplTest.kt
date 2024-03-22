package com.gabrielbmoro.moviedb.domain.usecases

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

@OptIn(ExperimentalCoroutinesApi::class)
class GetUpcomingMoviesUseCaseImplTest {
    private lateinit var repository: FakeRepository
    private lateinit var useCase: GetUpcomingMoviesUseCase

    @BeforeTest
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