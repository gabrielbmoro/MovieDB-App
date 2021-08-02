package com.gabrielbmoro.programmingchallenge.usecases

import com.gabrielbmoro.programmingchallenge.repository.MoviesRepository
import com.gabrielbmoro.programmingchallenge.repository.entities.Movie
import com.gabrielbmoro.programmingchallenge.repository.room.entities.FavoriteMovieDTO
import com.gabrielbmoro.programmingchallenge.usecases.mappers.FavoriteMovieMapper
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

    private val mapper: FavoriteMovieMapper = mockk()
    private val repository: MoviesRepository = mockk()
    private val testDispatcher = TestCoroutineDispatcher()

    private fun getFavoriteUseCase() = FavoriteMovieUseCase(repository, mapper)

    @Test
    fun `Favorite a movie`() {
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

        coEvery { mapper.map(movie = movie) }.answers {
            FavoriteMovieDTO(
                language = "en-US",
                votesAverage = 0f,
                popularity = 0f,
                imageUrl = "",
                releaseDate = "",
                title = "titleTest",
                overview = "overviewTest",
                id = null
            )
        }
        coEvery { repository.doAsFavorite(any()) }.answers { true }

        // act
        testDispatcher.runBlockingTest {
            favoriteUseCaseTest.execute(movie)
        }

        // assert
        coVerify { repository.doAsFavorite(any()) }
    }
}