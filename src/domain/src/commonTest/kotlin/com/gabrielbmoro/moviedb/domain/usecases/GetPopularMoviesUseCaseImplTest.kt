package com.gabrielbmoro.moviedb.domain.usecases

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

@OptIn(ExperimentalCoroutinesApi::class)
class GetPopularMoviesUseCaseImplTest {
    private lateinit var repository: FakeRepository
    private lateinit var useCase: GetPopularMoviesUseCase

    @BeforeTest
    fun before() {
        repository = FakeRepository()
        useCase = GetPopularMoviesUseCaseImpl(repository)
    }

    @Test
    fun `should be able to get all popular movies`() =
        runTest {
            // arrange
            repository.popularMovies = emptyList()

            // act
            val result = useCase.execute(GetPopularMoviesUseCase.Params(1))

            // assert
            assertEquals(emptyList(), result)
        }
}
