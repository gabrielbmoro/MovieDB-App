package com.gabrielbmoro.moviedb.domain.usecases

import com.gabrielbmoro.moviedb.domain.model.DataOrException
import com.gabrielbmoro.moviedb.domain.model.Movie
import com.gabrielbmoro.moviedb.repository.MoviesRepository
import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class FavoriteMovieUseCaseTest {

    private lateinit var repository: MoviesRepository
    private lateinit var useCase: FavoriteMovieUseCase

    @Before
    fun before() {
        repository = mockk()
        useCase = FavoriteMovieUseCase(repository)
    }

    @Test
    fun `should be able to favorite a movie`() {
        // arrange
        val movie = Movie.mockWhiteDragonNotFavorite()

        coEvery { repository.doAsFavorite(movie) }.returns(DataOrException(true))
        coEvery { repository.checkIsAFavoriteMovie(movie.title) }.returns(DataOrException(false))

        runTest {
            // act
            useCase.invoke(movie, true)

            // assert
            coVerify(exactly = 1) { repository.doAsFavorite(movie) }
        }
    }

    @Test
    fun `should be able to remove a movie from favorite list`() {
        // arrange
        val movie = Movie.mockWhiteDragonNotFavorite()

        coEvery { repository.unFavorite(movie.title) }.returns(DataOrException(true))
        coEvery { repository.checkIsAFavoriteMovie(movie.title) }.returns(DataOrException(true))

        runTest {
            // act
            useCase.invoke(movie, false)

            // assert
            coVerify(exactly = 1) { repository.unFavorite(movie.title) }
        }
    }

    @Test
    fun `should be able to avoid two favorite operations if movie is already a favorite one`() {
        // arrange
        val movie = Movie.mockWhiteDragonNotFavorite()

        coEvery { repository.doAsFavorite(movie) }.returns(DataOrException(true))
        coEvery { repository.checkIsAFavoriteMovie(movie.title) }.returns(DataOrException(true))

        runTest {
            // act
            val result = useCase.invoke(movie, true)

            // assert
            assertThat(result.exception).isInstanceOf(IllegalStateException::class.java)
        }
    }
}