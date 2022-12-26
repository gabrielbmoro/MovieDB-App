package com.gabrielbmoro.programmingchallenge.domain.usecases

import com.gabrielbmoro.programmingchallenge.domain.model.DataOrException
import com.gabrielbmoro.programmingchallenge.domain.model.Movie
import com.gabrielbmoro.programmingchallenge.domain.model.Page
import com.gabrielbmoro.programmingchallenge.repository.MoviesRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
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
        val popularMovies = Page(
            listOf(
                Movie(
                    2f,
                    "Dragão branco",
                    "https://dragaobranco.png",
                    "Movie where Vandame shows how a good Karate fighter fights",
                    "2002-02-21",
                    language = "pt-br",
                    popularity = 2f,
                    isFavorite = false
                )
            ),
            1,
            1
        )

        coEvery { repository.getPopularMovies(1) }.returns(
            DataOrException(popularMovies)
        )

        runTest {
            // act
            useCase(1)

            // assert
            coVerify(exactly = 1) { repository.getPopularMovies(1) }
        }
    }
}