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
    ): List<Movie> = moviesFromFilter

    override suspend fun getFavoriteMovies(): List<Movie> {
        return favoriteMovies
    }

    override suspend fun favorite(movie: Movie) {
        TODO("Not yet implemented")
    }

    override suspend fun unFavorite(movieTitle: String) {
        timesCallUnfavorite++
    }

    override suspend fun checkIsAFavoriteMovie(movieTitle: String): Boolean {
        timesCallCheckFavorite++
        return isFavoriteMovie
    }

    override suspend fun getVideoStreams(movieId: Long): List<VideoStream> {
        return videoStreams
    }

    override suspend fun getMovieDetail(movieId: Long): MovieDetail {
        return movieDetails
    }

    override suspend fun searchMovieBy(query: String): List<Movie> {
        return searchMovies
    }
}
