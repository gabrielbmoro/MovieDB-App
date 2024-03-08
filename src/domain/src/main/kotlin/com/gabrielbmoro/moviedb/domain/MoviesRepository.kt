package com.gabrielbmoro.moviedb.domain

import com.gabrielbmoro.moviedb.domain.entities.Movie
import com.gabrielbmoro.moviedb.domain.entities.MovieDetail
import com.gabrielbmoro.moviedb.domain.entities.VideoStream

interface MoviesRepository {
    suspend fun getFavoriteMovies(): List<Movie>

    suspend fun getPopularMovies(page: Int): List<Movie>

    suspend fun getTopRatedMovies(page: Int): List<Movie>

    suspend fun getUpcomingMovies(page: Int): List<Movie>

    suspend fun getNowPlayingMovies(page: Int): List<Movie>

    suspend fun favorite(movie: Movie)

    suspend fun unFavorite(movieTitle: String)

    suspend fun checkIsAFavoriteMovie(movieTitle: String): Boolean

    suspend fun getVideoStreams(movieId: Long): List<VideoStream>

    suspend fun getMovieDetail(movieId: Long): MovieDetail

    suspend fun searchMovieBy(query: String): List<Movie>
}
