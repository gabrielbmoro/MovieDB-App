package com.gabrielbmoro.moviedb.domain.usecases

import com.gabrielbmoro.moviedb.domain.entities.Movie
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import kotlin.test.assertEquals

class FavoriteMovieUseCaseImplTest {

    private lateinit var repository: FakeRepository
    private lateinit var useCase: FavoriteMovieUseCase

    @Before
    fun before() {
        repository = FakeRepository()
        useCase = FavoriteMovieUseCaseImpl(repository)
    }

    @Test
    fun `should be able to remove a movie from favorite list`() = runTest {
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

        repository.isFavoriteMovie = true

        // act
        useCase.execute(params)

        // assert
        assertEquals(1, repository.timesCallUnfavorite)
    }
}
