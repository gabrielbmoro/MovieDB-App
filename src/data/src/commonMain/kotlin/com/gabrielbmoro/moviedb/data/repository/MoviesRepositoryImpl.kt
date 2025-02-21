package com.gabrielbmoro.moviedb.data.repository

import com.gabrielbmoro.moviedb.data.repository.datasources.database.room.FavoriteMoviesDAO
import com.gabrielbmoro.moviedb.data.repository.datasources.database.toFavoriteMovieDTO
import com.gabrielbmoro.moviedb.data.repository.datasources.database.toMovie
import com.gabrielbmoro.moviedb.data.repository.datasources.ktor.ApiService
import com.gabrielbmoro.moviedb.data.repository.datasources.ktor.responses.MovieResponse
import com.gabrielbmoro.moviedb.domain.MoviesRepository
import com.gabrielbmoro.moviedb.domain.entities.Movie
import com.gabrielbmoro.moviedb.domain.entities.MovieDetail
import com.gabrielbmoro.moviedb.domain.entities.VideoStream

class MoviesRepositoryImpl(
    private val api: ApiService,
    private val favoriteMoviesDAO: FavoriteMoviesDAO,
) : MoviesRepository {

    override suspend fun getFavoriteMovies(): List<Movie> =
        favoriteMoviesDAO.allFavoriteMovies().map { it.toMovie() }

    override suspend fun getMoviesFromFilter(filter: String, page: Int): List<Movie> = fetchMovies(
        category = filter,
        page = page
    )

    private suspend fun fetchMovies(category: String, page: Int): List<Movie> =
        api.getMovies(category, page).results?.toDomain().orEmpty()

    override suspend fun favorite(movie: Movie) =
        favoriteMoviesDAO.saveFavorite(movie.toFavoriteMovieDTO())

    override suspend fun unFavorite(movieTitle: String) =
        favoriteMoviesDAO.removeFavorite(movieTitle)

    override suspend fun checkIsAFavoriteMovie(movieTitle: String): Boolean =
        favoriteMoviesDAO.isThereAMovie(movieTitle).isNotEmpty()

    override suspend fun getVideoStreams(movieId: Long): List<VideoStream> =
        api.getVideoStreams(movieId).toVideoStreams()

    override suspend fun getMovieDetail(movieId: Long): MovieDetail =
        api.getMovieDetails(movieId).toMovieDetail()

    override suspend fun searchMovieBy(query: String): List<Movie> =
        api.searchMovieBy(query).results?.toDomain().orEmpty()

    private fun List<MovieResponse>.toDomain(): List<Movie> = map { it.toMovie() }
}
