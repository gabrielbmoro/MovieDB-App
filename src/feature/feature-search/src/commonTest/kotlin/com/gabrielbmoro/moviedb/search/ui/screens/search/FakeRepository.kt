package com.gabrielbmoro.moviedb.search.ui.screens.search

import com.gabrielbmoro.moviedb.domain.MoviesRepository
import com.gabrielbmoro.moviedb.domain.entities.Movie
import com.gabrielbmoro.moviedb.domain.entities.MovieDetail
import com.gabrielbmoro.moviedb.domain.entities.VideoStream

class FakeRepository : MoviesRepository {
    lateinit var searchResult: List<Movie>
    override suspend fun getMoviesFromFilter(
        filter: String,
        page: Int,
    ): Result<List<Movie>> {
        return runCatching {
            error("Nothing implemented yet")
        }
    }

    override suspend fun getFavoriteMovies(): Result<List<Movie>> {
        return runCatching {
            error("Nothing implemented yet")
        }
    }

    override suspend fun favorite(movie: Movie): Result<Unit> {
        return runCatching {
            error("Nothing implemented yet")
        }
    }

    override suspend fun unFavorite(movieTitle: String): Result<Unit> {
        return runCatching {
            error("Nothing implemented yet")
        }
    }

    override suspend fun checkIsAFavoriteMovie(movieTitle: String): Result<Boolean> {
        return runCatching {
            error("Nothing implemented yet")
        }
    }

    override suspend fun getVideoStreams(movieId: Long): Result<List<VideoStream>> {
        return runCatching {
            error("Nothing implemented yet")
        }
    }

    override suspend fun getMovieDetail(movieId: Long): Result<MovieDetail> {
        return runCatching {
            error("Nothing implemented yet")
        }
    }

    override suspend fun searchMovieBy(query: String): Result<List<Movie>> {
        return Result.success(searchResult)
    }
}
