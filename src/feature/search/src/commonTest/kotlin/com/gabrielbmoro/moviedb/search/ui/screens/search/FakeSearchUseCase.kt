package com.gabrielbmoro.moviedb.search.ui.screens.search

import com.gabrielbmoro.moviedb.domain.MoviesRepository
import com.gabrielbmoro.moviedb.domain.entities.Movie
import com.gabrielbmoro.moviedb.domain.entities.MovieDetail
import com.gabrielbmoro.moviedb.domain.entities.VideoStream

class FakeRepository : MoviesRepository {
    lateinit var searchResult: List<Movie>
    override suspend fun getMoviesFromFilter(
        filter: String,
        page: Int
    ): List<Movie> {
        TODO("Not yet implemented")
    }

    override suspend fun getFavoriteMovies(): List<Movie> {
        TODO("Not yet implemented")
    }

    override suspend fun favorite(movie: Movie) {
        TODO("Not yet implemented")
    }

    override suspend fun unFavorite(movieTitle: String) {
        TODO("Not yet implemented")
    }

    override suspend fun checkIsAFavoriteMovie(movieTitle: String): Boolean {
        TODO("Not yet implemented")
    }

    override suspend fun getVideoStreams(movieId: Long): List<VideoStream> {
        TODO("Not yet implemented")
    }

    override suspend fun getMovieDetail(movieId: Long): MovieDetail {
        TODO("Not yet implemented")
    }

    override suspend fun searchMovieBy(query: String): List<Movie> {
        return searchResult
    }
}
