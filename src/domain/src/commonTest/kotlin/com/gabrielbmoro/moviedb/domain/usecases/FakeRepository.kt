package com.gabrielbmoro.moviedb.domain.usecases

import com.gabrielbmoro.moviedb.domain.MoviesRepository
import com.gabrielbmoro.moviedb.domain.entities.Movie
import com.gabrielbmoro.moviedb.domain.entities.MovieDetail
import com.gabrielbmoro.moviedb.domain.entities.VideoStream

class FakeRepository : MoviesRepository {
    var isFavoriteMovie: Boolean = false

    var timesCallUnfavorite: Int = 0
        private set

    var timesCallCheckFavorite: Int = 0
        private set

    lateinit var favoriteMovies: List<Movie>

    lateinit var movieDetails: MovieDetail

    lateinit var videoStreams: List<VideoStream>

    lateinit var searchMovies: List<Movie>

    lateinit var moviesFromFilter: List<Movie>

    override suspend fun getMoviesFromFilter(
        filter: String,
        page: Int,
    ): Result<List<Movie>> = Result.success(moviesFromFilter)

    override suspend fun getFavoriteMovies(): Result<List<Movie>> {
        return Result.success(favoriteMovies)
    }

    override suspend fun favorite(movie: Movie): Result<Unit> {
        return Result.success(Unit)
    }

    override suspend fun unFavorite(movieTitle: String): Result<Unit> {
        return runCatching {
            timesCallUnfavorite++
        }
    }

    override suspend fun checkIsAFavoriteMovie(movieTitle: String): Result<Boolean> {
        return runCatching {
            timesCallCheckFavorite++
            isFavoriteMovie
        }
    }

    override suspend fun getVideoStreams(movieId: Long): Result<List<VideoStream>> {
        return Result.success(videoStreams)
    }

    override suspend fun getMovieDetail(movieId: Long): Result<MovieDetail> {
        return Result.success(movieDetails)
    }

    override suspend fun searchMovieBy(query: String): Result<List<Movie>> {
        return Result.success(searchMovies)
    }
}
