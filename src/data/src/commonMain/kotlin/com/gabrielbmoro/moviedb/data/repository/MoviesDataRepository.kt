package com.gabrielbmoro.moviedb.data.repository

import com.gabrielbmoro.moviedb.data.repository.datasources.database.room.FavoriteMoviesDAO
import com.gabrielbmoro.moviedb.data.repository.datasources.database.room.dto.FavoriteMovieDTO
import com.gabrielbmoro.moviedb.data.repository.datasources.ktor.ApiService

class MoviesDataRepository(
    private val api: ApiService,
    private val favoriteMoviesDAO: FavoriteMoviesDAO,
) {

    suspend fun getFavoriteMovies() = favoriteMoviesDAO.allFavoriteMovies()

    suspend fun getMoviesFromFilter(filter: String, page: Int) = fetchMovies(
        category = filter,
        page = page,
    )

    private suspend fun fetchMovies(category: String, page: Int) =
        api.getMovies(category, page).results

    suspend fun favorite(movie: FavoriteMovieDTO) = favoriteMoviesDAO.saveFavorite(movie)

    suspend fun unFavorite(movieTitle: String) =
        favoriteMoviesDAO.removeFavorite(movieTitle)

    suspend fun checkIsAFavoriteMovie(movieTitle: String): Boolean =
        favoriteMoviesDAO.isThereAMovie(movieTitle).isNotEmpty()

    suspend fun getVideoStreams(movieId: Long) = api.getVideoStreams(movieId)

    suspend fun getMovieDetail(movieId: Long) = api.getMovieDetails(movieId)

    suspend fun searchMovieBy(query: String) = api.searchMovieBy(query).results
}
