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
class GetTopRatedMoviesUseCaseTest {

    private lateinit var repository: MoviesRepository
    private lateinit var useCase: GetTopRatedMoviesUseCase

    @Before
    fun before() {
        repository = mockk()
        useCase = GetTopRatedMoviesUseCaseImpl(repository)
    }

    @Test
    fun `should be able to get all top rated movies`() {
        // arrange
        every { repository.getTopRatedMovies() }.returns(emptyFlow())

        // act
        useCase()

        // assert
        verify(exactly = 1) { repository.getTopRatedMovies() }
    }
}
