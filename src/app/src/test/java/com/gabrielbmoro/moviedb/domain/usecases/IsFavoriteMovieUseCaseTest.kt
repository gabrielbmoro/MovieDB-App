package com.gabrielbmoro.moviedb.domain.usecases

import com.gabrielbmoro.moviedb.domain.model.DataOrException
import com.gabrielbmoro.moviedb.repository.MoviesRepository
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
class IsFavoriteMovieUseCaseTest {

    private lateinit var repository: MoviesRepository
    private lateinit var useCase: IsFavoriteMovieUseCase

    @Before
    fun before() {
        repository = mockk()
        useCase = IsFavoriteMovieUseCase(repository)
    }

    @Test
    fun `should be able to check if a movie is favorite`() {
        // arrange
        val movieTitle = "Dragão branco"

        coEvery { repository.checkIsAFavoriteMovie(movieTitle = movieTitle) }.returns(
            DataOrException(true)
        )

        runTest {
            // act
            useCase(movieTitle)

            // assert
            coVerify(exactly = 1) { repository.checkIsAFavoriteMovie(movieTitle) }
        }
    }
}