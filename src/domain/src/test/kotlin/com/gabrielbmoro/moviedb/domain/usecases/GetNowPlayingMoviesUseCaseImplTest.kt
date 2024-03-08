package com.gabrielbmoro.moviedb.domain.usecases

import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import kotlin.test.assertEquals

class GetNowPlayingMoviesUseCaseImplTest {

    private lateinit var repository: FakeRepository
    private lateinit var useCase: GetNowPlayingMoviesUseCase

    @Before
    fun before() {
        repository = FakeRepository()
        useCase = GetNowPlayingMoviesUseCaseImpl(repository)
    }

    @Test
    fun `should be able to get all now playing movies`() = runTest {
        // arrange
        repository.nowPlayingMovies = emptyList()

        // act
        val result = useCase.execute(GetNowPlayingMoviesUseCase.Params(1))

        // assert
        assertEquals(emptyList(), result)
    }
}