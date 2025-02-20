package com.gabrielbmoro.moviedb.domain.usecases

import com.gabrielbmoro.moviedb.domain.entities.Movie
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

@OptIn(ExperimentalCoroutinesApi::class)
class FavoriteMovieUseCaseImplTest {
    private lateinit var repository: FakeRepository
    private lateinit var useCase: FavoriteMovieUseCase

    @BeforeTest
    fun before() {
        repository = FakeRepository()
        useCase = FavoriteMovieUseCaseImpl(repository)
    }

    @Test
    fun `should be able to remove a movie from favorite list`() =
        runTest {
            // arrange
            val movie = Movie.mockWhiteDragonNotFavorite()
            val params =
                FavoriteMovieUseCase.Params(
                    movieId = movie.id,
                    movieTitle = movie.title,
                    movieBackdropImageUrl = movie.backdropImageUrl,
                    movieLanguage = movie.language,
                    movieOverview = movie.overview,
                    moviePopularity = movie.popularity,
                    moviePosterImageUrl = movie.posterImageUrl,
                    movieReleaseDate = movie.releaseDate,
                    movieVotesAverage = movie.votesAverage,
                    toFavorite = false,
                )

            repository.isFavoriteMovie = true

            // act
            useCase.execute(params)

            // assert
            assertEquals(1, repository.timesCallUnfavorite)
        }
}
