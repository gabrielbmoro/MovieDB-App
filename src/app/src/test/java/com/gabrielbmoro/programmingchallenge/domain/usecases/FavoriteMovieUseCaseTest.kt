package com.gabrielbmoro.programmingchallenge.domain.usecases

import com.gabrielbmoro.programmingchallenge.domain.model.DataOrException
import com.gabrielbmoro.programmingchallenge.domain.usecases.FavoriteMovieUseCase
import com.gabrielbmoro.programmingchallenge.repository.MoviesRepository
import com.gabrielbmoro.programmingchallenge.domain.model.Movie
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4


@ExperimentalCoroutinesApi
@RunWith(JUnit4::class)
class FavoriteMovieUseCaseTest {

    private val repository: MoviesRepository = mockk()
    private val testDispatcher = TestCoroutineDispatcher()

    private fun getFavoriteUseCase() = FavoriteMovieUseCase(repository)

    @Test
    fun `should be able to favorite a movie`() {
        // arrange
        val favoriteUseCaseTest = getFavoriteUseCase()
        val movie = Movie(
            isFavorite = true,
            overview = "overviewTest",
            releaseDate = "",
            imageUrl = "",
            popularity = 0f,
            votesAverage = 0f,
            language = "en-US",
            title = "titleTest"
        )

        coEvery { repository.doAsFavorite(any()) }.answers { DataOrException(data = true) }

        // act
        testDispatcher.runBlockingTest {
            favoriteUseCaseTest.execute(movie)
        }

        // assert
        coVerify { repository.doAsFavorite(any()) }
    }
}