package com.gabrielbmoro.moviedb.domain.usecases

import com.gabrielbmoro.moviedb.domain.MoviesRepository
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
class IsFavoriteMovieUseCaseImplTest {

    private lateinit var repository: MoviesRepository
    private lateinit var useCase: IsFavoriteMovieUseCase

    @Before
    fun before() {
        repository = mockk()
        useCase = IsFavoriteMovieUseCaseImpl(repository)
    }

    @Test
    fun `should be able to check if a movie is favorite`() = runTest {
        // arrange
        val movieTitle = "Dragão branco"

        coEvery { repository.checkIsAFavoriteMovie(movieTitle = movieTitle) }.returns(
            true
        )

        // act
        useCase.execute(IsFavoriteMovieUseCase.Params(movieTitle = movieTitle))

        // assert
        coVerify(exactly = 1) { repository.checkIsAFavoriteMovie(movieTitle) }
    }
}
