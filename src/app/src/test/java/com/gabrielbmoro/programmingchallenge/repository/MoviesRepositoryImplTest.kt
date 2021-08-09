package com.gabrielbmoro.programmingchallenge.repository

import com.gabrielbmoro.programmingchallenge.repository.entities.Movie
import com.gabrielbmoro.programmingchallenge.repository.retrofit.ApiRepository
import com.gabrielbmoro.programmingchallenge.repository.retrofit.responses.PageResponse
import com.gabrielbmoro.programmingchallenge.repository.room.FavoriteMoviesDAO
import com.gabrielbmoro.programmingchallenge.repository.room.entities.FavoriteMovieDTO
import com.gabrielbmoro.programmingchallenge.usecases.mappers.FavoriteMovieMapper
import com.google.common.truth.Truth
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import java.io.IOException

@ExperimentalCoroutinesApi
@RunWith(JUnit4::class)
class MoviesRepositoryImplTest {

    private val testDispatcher = TestCoroutineDispatcher()

    private val apiRepository: ApiRepository = mockk()
    private val favoriteMoviesDAO: FavoriteMoviesDAO = mockk()

    private val mockedMovie = Movie(
        imageUrl = "https://asodaksd.jpg",
        popularity = 5f,
        votesAverage = 10f,
        language = "pt-BR",
        title = "The King Kong",
        isFavorite = false,
        overview = "This movie is a great one",
        releaseDate = "12/02/2002",
    )

    private val fakeToken = "ab8622c4-2129-4824-9dd3-297ef7855942"

    private fun getRepository(): MoviesRepositoryImpl {
        return MoviesRepositoryImpl(
            api = apiRepository,
            favoriteMoviesDAO = favoriteMoviesDAO,
            apiToken = fakeToken
        )
    }

    @Test
    fun `should be able to get top rated movies`() {
        // arrange
        val pageTarget = 2
        val fakePageResponse = PageResponse(
            totalPages = 42,
            page = pageTarget,
            results = emptyList(),
            totalResults = 120
        )

        val repositoryTest = getRepository()
        coEvery {
            apiRepository.getTopRatedMovies(
                apiKey = fakeToken,
                pageNumber = pageTarget
            )
        }.answers { fakePageResponse }

        // act
        testDispatcher.runBlockingTest {
            repositoryTest.getTopRatedMovies(pageTarget)
        }

        // assert
        coVerify { apiRepository.getTopRatedMovies(fakeToken, pageTarget) }
    }

    @Test
    fun `should be able to get popular movies`() {
        // arrange
        val pageTarget = 2
        val fakePageResponse = PageResponse(
            totalPages = 42,
            page = pageTarget,
            results = emptyList(),
            totalResults = 120
        )

        val repositoryTest = getRepository()
        coEvery {
            apiRepository.getPopularMovies(
                apiKey = fakeToken,
                pageNumber = pageTarget
            )
        }.answers { fakePageResponse }

        // act
        testDispatcher.runBlockingTest {
            repositoryTest.getPopularMovies(pageTarget)
        }

        // assert
        coVerify { apiRepository.getPopularMovies(fakeToken, pageTarget) }
    }

    @Test
    fun `should be able to favorite a movie that is not favorite`() {
        // arrange
        val repositoryTest = getRepository()
        val favoriteMovie = FavoriteMovieMapper().map(movie = mockedMovie)
        coEvery { favoriteMoviesDAO.isThereAMovie(title = mockedMovie.title) }.returns(emptyList())
        coEvery { favoriteMoviesDAO.saveFavorite(favoriteMovie) }.answers { }

        // act
        testDispatcher.runBlockingTest {
            val result = repositoryTest.doAsFavorite(favoriteMovie)

            // assert
            Truth.assertThat(result).isTrue()
        }

        coVerify { favoriteMoviesDAO.saveFavorite(favoriteMovie) }
    }

    @Test
    fun `should be able to remove from favorites a movie that is favorite`() {
        // arrange
        val repositoryTest = getRepository()
        val favoriteMovie = FavoriteMovieMapper().map(movie = mockedMovie)
        coEvery { repositoryTest.checkIsAFavoriteMovie(favoriteMovie) }.returns(true)
        coEvery { favoriteMoviesDAO.removeFavorite(favoriteMovie.title) }.answers { }

        // act
        testDispatcher.runBlockingTest {
            val result = repositoryTest.unFavorite(favoriteMovie.title)

            // assert
            Truth.assertThat(result).isTrue()
        }

        coVerify { favoriteMoviesDAO.removeFavorite(favoriteMovie.title) }
    }

    @Test
    fun `should not be able to remove from favorites a movie that is not favorite`() {
        // arrange
        val repositoryTest = getRepository()
        val favoriteMovie = FavoriteMovieMapper().map(movie = mockedMovie)
        coEvery { favoriteMoviesDAO.removeFavorite(favoriteMovie.title) }.answers { }

        // act
        testDispatcher.runBlockingTest {
            val result = repositoryTest.unFavorite(favoriteMovie.title)

            // assert
            Truth.assertThat(result).isTrue()
        }

        coVerify { favoriteMoviesDAO.removeFavorite(favoriteMovie.title) }
    }

    @Test
    fun `should not be able to favorite a movie that is already favorite`() {
        // arrange
        val repositoryTest = getRepository()
        val favoriteMovie = FavoriteMovieMapper().map(movie = mockedMovie)
        coEvery { favoriteMoviesDAO.isThereAMovie(title = mockedMovie.title) }.returns(
            listOf(
                FavoriteMovieDTO(
                    id = null,
                    language = mockedMovie.language,
                    votesAverage = mockedMovie.votesAverage,
                    popularity = mockedMovie.popularity,
                    imageUrl = mockedMovie.imageUrl,
                    releaseDate = mockedMovie.releaseDate,
                    title = mockedMovie.title,
                    overview = mockedMovie.overview
                )
            )
        )

        // act
        testDispatcher.runBlockingTest {
            val result = repositoryTest.doAsFavorite(favoriteMovie)

            // assert
            Truth.assertThat(result).isTrue()
        }

        // assert
        coVerify(exactly = 0) { favoriteMoviesDAO.saveFavorite(favoriteMovie) }
    }

    @Test
    fun `should be able to check if the movie is favorite when there is one`() {
        // arrange
        val repositoryTest = getRepository()
        val favoriteMovie = FavoriteMovieMapper().map(movie = mockedMovie)
        coEvery { favoriteMoviesDAO.isThereAMovie(favoriteMovie.title) }.returns(
            listOf(
                favoriteMovie
            )
        )

        // act
        testDispatcher.runBlockingTest {
            val isFavorite = repositoryTest.checkIsAFavoriteMovie(favoriteMovie)

            // assert
            Truth.assertThat(isFavorite).isTrue()
        }
    }

    @Test
    fun `should be able to check if the movie is favorite when there is no one`() {
        // arrange
        val repositoryTest = getRepository()
        val favoriteMovie = FavoriteMovieMapper().map(movie = mockedMovie)
        coEvery { favoriteMoviesDAO.isThereAMovie(favoriteMovie.title) }.returns(emptyList())

        // act
        testDispatcher.runBlockingTest {
            val isFavorite = repositoryTest.checkIsAFavoriteMovie(favoriteMovie)

            // assert
            Truth.assertThat(isFavorite).isFalse()
        }
    }

    @Test
    fun `should be able to get all favorites movies`() {
        // arrange
        val repositoryTest = getRepository()
        coEvery { favoriteMoviesDAO.allFavoriteMovies() }.returns(emptyList())

        // act
        testDispatcher.runBlockingTest {
            repositoryTest.getFavoriteMovies()
        }

        // assert
        coVerify { favoriteMoviesDAO.allFavoriteMovies() }
    }

    @Test
    fun `should be able to handle an exception when there is an attempt to favorite a movie`() {
        // arrange
        val repositoryTest = getRepository()
        val favoriteMovie = FavoriteMovieMapper().map(movie = mockedMovie)
        coEvery { favoriteMoviesDAO.saveFavorite(favoriteMovie) }.throws(IOException("Error simulated"))

        // act
        testDispatcher.runBlockingTest {
            val wasOperationSuccess = repositoryTest.doAsFavorite(favoriteMovie)

            // assert
            Truth.assertThat(wasOperationSuccess).isFalse()
        }
    }

    @Test
    fun `should be able to handle an exception when occurs movie remove operation from the favorite list`() {
        // arrange
        val repositoryTest = getRepository()
        val favoriteMovie = FavoriteMovieMapper().map(movie = mockedMovie)
        coEvery { favoriteMoviesDAO.removeFavorite(favoriteMovie.title) }.throws(IOException("Error simulated"))

        // act
        testDispatcher.runBlockingTest {
            val wasOperationSuccess = repositoryTest.unFavorite(favoriteMovie.title)

            // assert
            Truth.assertThat(wasOperationSuccess).isFalse()
        }
    }
}