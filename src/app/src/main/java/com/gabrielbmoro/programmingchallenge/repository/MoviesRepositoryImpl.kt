package com.gabrielbmoro.programmingchallenge.repository

import com.gabrielbmoro.programmingchallenge.repository.retrofit.ApiRepository
import com.gabrielbmoro.programmingchallenge.repository.retrofit.responses.PageResponse
import com.gabrielbmoro.programmingchallenge.repository.room.FavoriteMoviesDAO
import com.gabrielbmoro.programmingchallenge.repository.room.entities.FavoriteMovieDTO

class MoviesRepositoryImpl(
    private val api: ApiRepository,
    private val favoriteMoviesDAO: FavoriteMoviesDAO,
    private val apiToken: String
) : MoviesRepository {

    override suspend fun getFavoriteMovies(): List<FavoriteMovieDTO> {
        return favoriteMoviesDAO.allFavoriteMovies()
    }

    override suspend fun getPopularMovies(pageNumber: Int): PageResponse {
        return api.getPopularMovies(
            pageNumber = pageNumber,
            apiKey = apiToken
        )
    }

    override suspend fun getTopRatedMovies(pageNumber: Int): PageResponse {
        return api.getTopRatedMovies(
            pageNumber = pageNumber,
            apiKey = apiToken
        )
    }

    override suspend fun doAsFavorite(movie: FavoriteMovieDTO): Boolean {
        return try {
            if (!checkIsAFavoriteMovie(movie)) {
                favoriteMoviesDAO.saveFavorite(movie)
            }
            true
        } catch (exception: Exception) {
            false
        }
    }

    override suspend fun unFavorite(movieTitle: String): Boolean {
        return try {
            favoriteMoviesDAO.removeFavorite(movieTitle)
            true
        } catch (exception: Exception) {
            false
        }
    }

    override suspend fun checkIsAFavoriteMovie(movie: FavoriteMovieDTO): Boolean {
        val movieTitle = movie.title
        return favoriteMoviesDAO.isThereAMovie(
            title = movieTitle
        ).any()
    }
}