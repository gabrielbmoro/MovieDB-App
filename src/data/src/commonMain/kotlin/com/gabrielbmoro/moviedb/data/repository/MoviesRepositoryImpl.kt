package com.gabrielbmoro.moviedb.data.repository

import com.gabrielbmoro.moviedb.data.repository.datasources.database.Database
import com.gabrielbmoro.moviedb.data.repository.datasources.ktor.ApiService
import com.gabrielbmoro.moviedb.domain.MoviesRepository
import com.gabrielbmoro.moviedb.domain.entities.Movie
import com.gabrielbmoro.moviedb.domain.entities.MovieDetail
import com.gabrielbmoro.moviedb.domain.entities.VideoStream

class MoviesRepositoryImpl(
    private val api: ApiService,
    private val database: Database
) : MoviesRepository {

    override suspend fun getFavoriteMovies(): List<Movie> {
        return database.allFavoriteMovies()
    }

    override suspend fun getPopularMovies(page: Int): List<Movie> {
        val movies = api.getPopularMovies(pageNumber = page)
        return movies.results?.map { movieResponse ->
            movieResponse.toMovie()
        } ?: emptyList()
    }

    override suspend fun getTopRatedMovies(page: Int): List<Movie> {
        val movies = api.getTopRatedMovies(pageNumber = page)
        return movies.results?.map { movieResponse ->
            movieResponse.toMovie()
        } ?: emptyList()
    }

    override suspend fun getUpcomingMovies(page: Int): List<Movie> {
        val movies = api.getUpcomingMovies(pageNumber = page)
        return movies.results?.map { movieResponse ->
            movieResponse.toMovie()
        } ?: emptyList()
    }

    override suspend fun getNowPlayingMovies(page: Int): List<Movie> {
        val movies = api.getNowPlayingMovies(pageNumber = page)
        return movies.results?.map { movieResponse ->
            movieResponse.toMovie()
        } ?: emptyList()
    }

    override suspend fun favorite(movie: Movie) {
        database.saveFavorite(movie)
    }

    override suspend fun unFavorite(movieTitle: String) {
        database.removeFavorite(movieTitle)
    }

    override suspend fun checkIsAFavoriteMovie(movieTitle: String): Boolean {
        return database.isThereAMovie(
            title = movieTitle
        ).any()
    }

    override suspend fun getVideoStreams(movieId: Long): List<VideoStream> {
        val result = api.getVideoStreams(
            movieId = movieId
        )
        return result.toVideoStreams()
    }

    override suspend fun getMovieDetail(movieId: Long): MovieDetail {
        val result = api.getMovieDetails(
            movieId = movieId
        )
        return result.toMovieDetail()
    }

    override suspend fun searchMovieBy(query: String): List<Movie> {
        val result = api.searchMovieBy(query = query)
        return result.results?.map { it.toMovie() } ?: emptyList()
    }
}
