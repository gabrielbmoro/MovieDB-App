package com.gabrielbmoro.moviedb.domain.usecases

import com.gabrielbmoro.moviedb.domain.MoviesRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@ExperimentalCoroutinesApi
@RunWith(JUnit4::class)
class GetTopRatedMoviesUseCaseTest {

    private lateinit var repository: MoviesRepository
    private lateinit var useCase: GetTopRatedMoviesUseCase

    @Before
    fun before() {
        repository = mockk()
        useCase = GetTopRatedMoviesUseCaseImpl(repository)
    }

    @Test
    fun `should be able to get all top rated movies`() = runTest {
        // arrange
        coEvery { repository.getTopRatedMovies(1) }.returns(emptyList())

        // act
        useCase.execute(GetTopRatedMoviesUseCase.Params(1))

        // assert
        coVerify(exactly = 1) { repository.getTopRatedMovies(1) }
    }
}
