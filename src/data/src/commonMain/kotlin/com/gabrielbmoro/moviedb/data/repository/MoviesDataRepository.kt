package com.gabrielbmoro.moviedb.data.repository

import com.gabrielbmoro.moviedb.data.repository.datasources.database.room.FavoriteMoviesDAO
import com.gabrielbmoro.moviedb.data.repository.datasources.ktor.ApiService
import com.gabrielbmoro.moviedb.data.repository.mappers.toFavoriteMovieDTO
import com.gabrielbmoro.moviedb.data.repository.mappers.toMovie
import com.gabrielbmoro.moviedb.data.repository.mappers.toMovieDetail
import com.gabrielbmoro.moviedb.data.repository.mappers.toVideoStreams
import com.gabrielbmoro.moviedb.domain.MoviesRepository
import com.gabrielbmoro.moviedb.domain.model.Movie

internal class MoviesDataRepository(
    private val api: ApiService,
    private val favoriteMoviesDAO: FavoriteMoviesDAO,
) : MoviesRepository {

    override suspend fun getFavoriteMovies() = favoriteMoviesDAO.allFavoriteMovies().map {
        it.toMovie()
    }

    override suspend fun getMoviesFromFilter(filter: String, page: Int) = fetchMovies(
        category = filter,
        page = page,
    )?.map { it.toMovie() }.orEmpty()

    private suspend fun fetchMovies(category: String, page: Int) =
        api.getMovies(category, page).results

    override suspend fun favorite(movie: Movie) = favoriteMoviesDAO.saveFavorite(
        movie.toFavoriteMovieDTO(),
    )

    override suspend fun unFavorite(movieTitle: String) =
        favoriteMoviesDAO.removeFavorite(movieTitle)

    override suspend fun checkIsAFavoriteMovie(movieTitle: String): Boolean =
        favoriteMoviesDAO.isThereAMovie(movieTitle).isNotEmpty()

    override suspend fun getVideoStreams(movieId: Long) =
        api.getVideoStreams(movieId).toVideoStreams()

    override suspend fun getMovieDetail(movieId: Long) =
        api.getMovieDetails(movieId).toMovieDetail()

    override suspend fun searchMovieBy(query: String) =
        api.searchMovieBy(query).results?.map { it.toMovie() }.orEmpty()
}
