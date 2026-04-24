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
    ): List<Movie> {
        error("Nothing implemented yet")
    }

    override suspend fun getFavoriteMovies(): List<Movie> {
        error("Nothing implemented yet")
    }

    override suspend fun favorite(movie: Movie) = Unit

    override suspend fun unFavorite(movieTitle: String) = Unit

    override suspend fun checkIsAFavoriteMovie(movieTitle: String): Boolean {
        error("Nothing implemented yet")
    }

    override suspend fun getVideoStreams(movieId: Long): List<VideoStream> {
        error("Nothing implemented yet")
    }

    override suspend fun getMovieDetail(movieId: Long): MovieDetail {
        error("Nothing implemented yet")
    }

    override suspend fun searchMovieBy(query: String): List<Movie> {
        return searchResult
    }
}
