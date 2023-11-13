package com.gabrielbmoro.moviedb.wishlist.domain.usecases

import com.gabrielbmoro.moviedb.repository.MoviesRepository
import com.gabrielbmoro.moviedb.repository.model.Movie
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetFavoriteMoviesUseCase @Inject constructor(
    private val repository: MoviesRepository
) {

    operator fun invoke(): Flow<List<Movie>> = repository.getFavoriteMovies()
}
