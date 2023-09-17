package com.gabrielbmoro.moviedb.movies.domain.usecases

import com.gabrielbmoro.moviedb.repository.MoviesRepository
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.emptyFlow
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@ExperimentalCoroutinesApi
@RunWith(JUnit4::class)
class GetPopularMoviesUseCaseTest {

    private lateinit var repository: MoviesRepository
    private lateinit var useCase: GetPopularMoviesUseCase

    @Before
    fun before() {
        repository = mockk()
        useCase = GetPopularMoviesUseCase(repository)
    }

    @Test
    fun `should be able to get all popular movies`() {
        // arrange
        every { repository.getPopularMovies() }.returns(emptyFlow())

        // act
        useCase()

        // assert
        verify(exactly = 1) { repository.getPopularMovies() }
    }
}
