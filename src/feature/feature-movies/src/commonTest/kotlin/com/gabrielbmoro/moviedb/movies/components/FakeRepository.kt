package com.gabrielbmoro.moviedb.movies.components

import com.gabrielbmoro.moviedb.domain.MoviesRepository
import com.gabrielbmoro.moviedb.domain.entities.Movie

class FakeRepository : MoviesRepository {

    lateinit var filteredMovies: List<Movie>

    override suspend fun getMoviesFromFilter(
        filter: String,
        page: Int,
    ): Result<List<Movie>> {
        return Result.success(filteredMovies)
    }

    override suspend fun getFavoriteMovies() = runCatching {
        error("Not yet implemented")
    }

    override suspend fun favorite(movie: Movie) = runCatching {
        error("Not yet implemented")
    }

    override suspend fun unFavorite(movieTitle: String) = runCatching {
        error("Not yet implemented")
    }

    override suspend fun checkIsAFavoriteMovie(movieTitle: String) = runCatching {
        error("Not yet implemented")
    }

    override suspend fun getVideoStreams(movieId: Long) = runCatching {
        error("Not yet implemented")
    }

    override suspend fun getMovieDetail(movieId: Long) = runCatching {
        error("Not yet implemented")
    }

    override suspend fun searchMovieBy(query: String) = runCatching {
        error("Not yet implemented")
    }
}
