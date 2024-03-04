package com.gabrielbmoro.moviedb.domain.usecases

import com.gabrielbmoro.moviedb.domain.MoviesRepository
import com.gabrielbmoro.moviedb.domain.entities.Movie
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

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
        return withContext(Dispatchers.IO) {
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
}
