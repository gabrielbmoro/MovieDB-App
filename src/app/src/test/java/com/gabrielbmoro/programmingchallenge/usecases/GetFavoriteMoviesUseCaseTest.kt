package com.gabrielbmoro.programmingchallenge.usecases

import com.gabrielbmoro.programmingchallenge.repository.MoviesRepository
import com.gabrielbmoro.programmingchallenge.repository.entities.Movie
import com.gabrielbmoro.programmingchallenge.repository.room.entities.FavoriteMovieDTO
import com.gabrielbmoro.programmingchallenge.usecases.mappers.MovieMapper
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
class GetFavoriteMoviesUseCaseTest {

    private val repository: MoviesRepository = mockk()
    private val mapper: MovieMapper = mockk()
    private val testDispatcher = TestCoroutineDispatcher()

    private fun getFavoriteMovieUseCaseTest() = GetFavoriteMoviesUseCase(repository, mapper)

    @Test
    fun `should be able to get all favorite movies`() {
        // arrange
        val favoriteUseCaseTest = getFavoriteMovieUseCaseTest()
        coEvery {
            mapper.mapFavorite(any())
        }.answers {
            Movie(
                isFavorite = true,
                title = "",
                language = "",
                votesAverage = 0f,
                popularity = 0f,
                imageUrl = "",
                releaseDate = "",
                overview = ""
            )
        }
        coEvery { repository.getFavoriteMovies() }.answers {
            listOf(
                FavoriteMovieDTO(
                    id = 1,
                    overview = "",
                    releaseDate = "",
                    imageUrl = "",
                    popularity = 0f,
                    votesAverage = 0f,
                    language = "",
                    title = ""
                ),
                FavoriteMovieDTO(
                    id = 2,
                    overview = "",
                    releaseDate = "",
                    imageUrl = "",
                    popularity = 0f,
                    votesAverage = 0f,
                    language = "",
                    title = ""
                )
            )
        }

        // act
        testDispatcher.runBlockingTest {
            favoriteUseCaseTest.execute()
        }

        // assert
        coVerify(exactly = 2) { mapper.mapFavorite(any()) }
    }

    @Test
    fun `Get favorite movies with no record`() {
        // arrange
        val favoriteUseCaseTest = getFavoriteMovieUseCaseTest()
        coEvery {
            mapper.mapFavorite(any())
        }.answers {
            Movie(
                isFavorite = true,
                title = "",
                language = "",
                votesAverage = 0f,
                popularity = 0f,
                imageUrl = "",
                releaseDate = "",
                overview = ""
            )
        }
        coEvery { repository.getFavoriteMovies() }.answers { emptyList() }

        // act
        testDispatcher.runBlockingTest {
            favoriteUseCaseTest.execute()
        }

        // assert
        coVerify(exactly = 0) { mapper.mapFavorite(any()) }
    }
}