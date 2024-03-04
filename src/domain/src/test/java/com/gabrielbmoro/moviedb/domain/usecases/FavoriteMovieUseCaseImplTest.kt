package com.gabrielbmoro.moviedb.domain.usecases

import com.gabrielbmoro.moviedb.domain.MoviesRepository
import com.gabrielbmoro.moviedb.domain.entities.Movie
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class FavoriteMovieUseCaseImplTest {

    private lateinit var repository: MoviesRepository
    private lateinit var useCase: FavoriteMovieUseCase

    @Before
    fun before() {
        repository = mockk()
        useCase = FavoriteMovieUseCaseImpl(repository)
    }

    @Test
    fun `should be able to favorite a movie`() {
        // arrange
        val movie = Movie.mockWhiteDragonNotFavorite()
        val params = FavoriteMovieUseCase.Params(
            movieId = movie.id,
            movieTitle = movie.title,
            movieBackdropImageUrl = movie.backdropImageUrl,
            movieLanguage = movie.language,
            movieOverview = movie.overview,
            moviePopularity = movie.popularity,
            moviePosterImageUrl = movie.posterImageUrl,
            movieReleaseDate = movie.releaseDate,
            movieVotesAverage = movie.votesAverage,
            toFavorite = true
        )
        coEvery { repository.favorite(movie) }.answers {}
        coEvery { repository.checkIsAFavoriteMovie(movie.title) }.returns(false)

        runTest {
            // act
            useCase.execute(params)

            // assert
            coVerify(exactly = 1) { repository.favorite(movie) }
        }
    }

    @Test
    fun `should be able to remove a movie from favorite list`() {
        // arrange
        val movie = Movie.mockWhiteDragonNotFavorite()
        val params = FavoriteMovieUseCase.Params(
            movieId = movie.id,
            movieTitle = movie.title,
            movieBackdropImageUrl = movie.backdropImageUrl,
            movieLanguage = movie.language,
            movieOverview = movie.overview,
            moviePopularity = movie.popularity,
            moviePosterImageUrl = movie.posterImageUrl,
            movieReleaseDate = movie.releaseDate,
            movieVotesAverage = movie.votesAverage,
            toFavorite = false
        )

        coEvery { repository.unFavorite(movie.title) }.answers {}
        coEvery { repository.checkIsAFavoriteMovie(movie.title) }.returns(true)

        runTest {
            // act
            useCase.execute(params)

            // assert
            coVerify(exactly = 1) { repository.unFavorite(movie.title) }
        }
    }
}
