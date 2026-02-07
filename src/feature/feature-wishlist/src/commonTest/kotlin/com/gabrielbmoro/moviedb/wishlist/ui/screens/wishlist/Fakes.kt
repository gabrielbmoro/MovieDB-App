package com.gabrielbmoro.moviedb.wishlist.ui.screens.wishlist

import com.gabrielbmoro.moviedb.domain.MoviesRepository
import com.gabrielbmoro.moviedb.domain.entities.Movie
import com.gabrielbmoro.moviedb.domain.entities.MovieDetail
import com.gabrielbmoro.moviedb.domain.entities.VideoStream
import com.gabrielbmoro.moviedb.domain.usecases.FavoriteMovieUseCase

class FakeRepository : MoviesRepository {
    lateinit var favoriteMovies: List<Movie>
    var isFavorite: Boolean = false

    override suspend fun getMoviesFromFilter(
        filter: String,
        page: Int,
    ): Result<List<Movie>> {
        TODO("Not yet implemented")
    }

    override suspend fun getFavoriteMovies(): Result<List<Movie>> {
        return Result.success(favoriteMovies)
    }

    override suspend fun favorite(movie: Movie): Result<Unit> {
        return Result.success(Unit)
    }

    override suspend fun unFavorite(movieTitle: String): Result<Unit> {
        return Result.success(Unit)
    }

    override suspend fun checkIsAFavoriteMovie(movieTitle: String): Result<Boolean> {
        return Result.success(isFavorite)
    }

    override suspend fun getVideoStreams(movieId: Long): Result<List<VideoStream>> {
        return Result.success(emptyList())
    }

    override suspend fun getMovieDetail(movieId: Long): Result<MovieDetail> {
        return runCatching { error("Something went wrong") }
    }

    override suspend fun searchMovieBy(query: String): Result<List<Movie>> {
        return runCatching { error("Something went wrong") }
    }
}

class FakeFavoriteMovieUseCase : FavoriteMovieUseCase {
    var timesCalled: Int = 0
        private set

    override suspend fun execute(input: FavoriteMovieUseCase.Params): Result<Unit> {
        return runCatching {
            timesCalled++
        }
    }
}
