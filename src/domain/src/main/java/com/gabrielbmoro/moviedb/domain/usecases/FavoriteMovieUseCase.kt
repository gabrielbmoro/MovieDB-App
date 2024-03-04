package com.gabrielbmoro.moviedb.domain.usecases

import com.gabrielbmoro.moviedb.domain.MoviesRepository
import com.gabrielbmoro.moviedb.domain.entities.Movie

interface FavoriteMovieUseCase {
    suspend operator fun invoke(
        movie: Movie,
        toFavorite: Boolean
    )
}

open class FavoriteMovieUseCaseImpl(
    private val repository: MoviesRepository
) : FavoriteMovieUseCase {

    override suspend operator fun invoke(
        movie: Movie,
        toFavorite: Boolean
    ) {
        when {
            (toFavorite && !repository.checkIsAFavoriteMovie(movie.title)) -> {
                repository.favorite(movie)
            }

            (!toFavorite && repository.checkIsAFavoriteMovie(movie.title)) -> {
                repository.unFavorite(movie.title)
            }
        }
    }
}
