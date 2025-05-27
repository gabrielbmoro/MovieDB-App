package com.gabrielbmoro.moviedb.domain

import com.gabrielbmoro.moviedb.data.repository.MoviesDataRepository
import com.gabrielbmoro.moviedb.domain.entities.Movie
import com.gabrielbmoro.moviedb.domain.entities.MovieDetail
import com.gabrielbmoro.moviedb.domain.entities.VideoStream
import com.gabrielbmoro.moviedb.domain.mappers.toFavoriteMovieDTO
import com.gabrielbmoro.moviedb.domain.mappers.toMovie
import com.gabrielbmoro.moviedb.domain.mappers.toMovieDetail
import com.gabrielbmoro.moviedb.domain.mappers.toVideoStreams

interface MoviesRepository {
    suspend fun getMoviesFromFilter(filter: String, page: Int): List<Movie>

    suspend fun getFavoriteMovies(): List<Movie>

    suspend fun favorite(movie: Movie)

    suspend fun unFavorite(movieTitle: String)

    suspend fun checkIsAFavoriteMovie(movieTitle: String): Boolean

    suspend fun getVideoStreams(movieId: Long): List<VideoStream>

    suspend fun getMovieDetail(movieId: Long): MovieDetail

    suspend fun searchMovieBy(query: String): List<Movie>
}

internal class MoviesRepositoryImpl(
    private val dataRepository: MoviesDataRepository,
) : MoviesRepository {
    override suspend fun getMoviesFromFilter(
        filter: String,
        page: Int,
    ): List<Movie> {
        return dataRepository.getMoviesFromFilter(
            filter = filter,
            page = page,
        )?.map { it.toMovie() }.orEmpty()
    }

    override suspend fun getFavoriteMovies(): List<Movie> {
        return dataRepository.getFavoriteMovies().map { it.toMovie() }
    }

    override suspend fun favorite(movie: Movie) {
        dataRepository.favorite(movie.toFavoriteMovieDTO())
    }

    override suspend fun unFavorite(movieTitle: String) {
        dataRepository.unFavorite(movieTitle)
    }

    override suspend fun checkIsAFavoriteMovie(movieTitle: String): Boolean {
        return dataRepository.checkIsAFavoriteMovie(movieTitle)
    }

    override suspend fun getVideoStreams(movieId: Long): List<VideoStream> {
        return dataRepository.getVideoStreams(movieId).toVideoStreams()
    }

    override suspend fun getMovieDetail(movieId: Long): MovieDetail {
        return dataRepository.getMovieDetail(movieId).toMovieDetail()
    }

    override suspend fun searchMovieBy(query: String): List<Movie> {
        return dataRepository.searchMovieBy(query)?.map { it.toMovie() }.orEmpty()
    }
}
