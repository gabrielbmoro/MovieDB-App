package com.gabrielbmoro.moviedb.movies.fakes

import com.gabrielbmoro.moviedb.domain.MoviesRepository
import com.gabrielbmoro.moviedb.domain.model.Movie
import com.gabrielbmoro.moviedb.domain.model.MovieDetail
import com.gabrielbmoro.moviedb.domain.model.VideoStream

class FakeRepository(
    private val getMoviesFromFilterError: Throwable? = null,
) : MoviesRepository {

    lateinit var filteredMovies: List<Movie>

    override suspend fun getMoviesFromFilter(
        filter: String,
        page: Int,
    ): List<Movie> {
        getMoviesFromFilterError?.let {
            throw it
        }
        return filteredMovies
    }

    override suspend fun getFavoriteMovies(): List<Movie> {
        error("Not yet implemented")
    }

    override suspend fun favorite(movie: Movie) = Unit

    override suspend fun unFavorite(movieTitle: String) = Unit

    override suspend fun checkIsAFavoriteMovie(movieTitle: String): Boolean {
        error("Not yet implemented")
    }

    override suspend fun getVideoStreams(movieId: Long): List<VideoStream> {
        error("Not yet implemented")
    }

    override suspend fun getMovieDetail(movieId: Long): MovieDetail {
        error("Not yet implemented")
    }

    override suspend fun searchMovieBy(query: String): List<Movie> {
        error("Not yet implemented")
    }
}
