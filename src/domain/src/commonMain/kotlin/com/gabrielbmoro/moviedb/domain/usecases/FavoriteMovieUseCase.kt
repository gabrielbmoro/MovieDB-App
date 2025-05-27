package com.gabrielbmoro.moviedb.domain.usecases

import com.gabrielbmoro.moviedb.domain.MoviesRepository
import com.gabrielbmoro.moviedb.domain.entities.Movie

interface FavoriteMovieUseCase : UseCase<FavoriteMovieUseCase.Params, Unit> {
    data class Params(
        val movieId: Long? = null,
        val movieVotesAverage: Float? = null,
        val moviePosterImageUrl: String? = null,
        val movieBackdropImageUrl: String? = null,
        val movieOverview: String? = null,
        val movieReleaseDate: String? = null,
        val movieLanguage: String? = null,
        val moviePopularity: Float? = null,
        val movieTitle: String,
        val toFavorite: Boolean,
    )
}

internal class FavoriteMovieUseCaseImpl(
    private val repository: MoviesRepository,
) : FavoriteMovieUseCase {
    override suspend fun execute(input: FavoriteMovieUseCase.Params) {
        val toFavorite = input.toFavorite
        val movieTitle = input.movieTitle
        when {
            (toFavorite && !repository.checkIsAFavoriteMovie(movieTitle)) -> {
                val movie =
                    Movie(
                        id = input.movieId!!,
                        votesAverage = input.movieVotesAverage!!,
                        title = input.movieTitle,
                        posterImageUrl = input.moviePosterImageUrl!!,
                        backdropImageUrl = input.movieBackdropImageUrl!!,
                        overview = input.movieOverview!!,
                        releaseDate = input.movieReleaseDate!!,
                        isFavorite = true,
                        language = input.movieLanguage!!,
                        popularity = input.moviePopularity!!,
                    )
                repository.favorite(movie = movie)
            }

            (!toFavorite && repository.checkIsAFavoriteMovie(movieTitle)) -> {
                repository.unFavorite(movieTitle)
            }
        }
    }
}
