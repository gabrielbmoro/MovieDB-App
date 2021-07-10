package com.gabrielbmoro.programmingchallenge.repository

import com.gabrielbmoro.programmingchallenge.core.di.ConfigVariables
import com.gabrielbmoro.programmingchallenge.repository.entities.Movie
import com.gabrielbmoro.programmingchallenge.repository.retrofit.ApiRepository
import com.gabrielbmoro.programmingchallenge.repository.entities.PageMovies
import com.gabrielbmoro.programmingchallenge.repository.room.FavoriteMoviesDAO

class MoviesRepositoryImpl(
    private val api: ApiRepository,
    private val favoriteMoviesDAO: FavoriteMoviesDAO
) : MoviesRepository {

    override suspend fun getFavoriteMovies(): List<Movie> {
        return favoriteMoviesDAO.allFavoriteMovies()
    }

    override suspend fun getPopularMovies(pageNumber: Int): PageMovies {
        return api.getMovies(
            pageNumber = pageNumber,
            apiKey = ConfigVariables.TOKEN,
            sortBy = ConfigVariables.PARAMETER_POPULAR_MOVIES
        )
    }

    override suspend fun getTopRatedMovies(pageNumber: Int): PageMovies {
        return api.getMovies(
            pageNumber = pageNumber,
            apiKey = ConfigVariables.TOKEN,
            sortBy = ConfigVariables.PARAMETER_TOP_RATED_MOVIES
        )
    }

    override suspend fun doAsFavorite(movie: Movie): Boolean {
        return try {
            if (!checkIsAFavoriteMovie(movie)) {
                movie.isFavorite = true
                favoriteMoviesDAO.saveFavorite(movie)
            }
            true
        } catch (exception: Exception) {
            false
        }
    }

    override suspend fun unFavorite(movie: Movie): Boolean {
        return try {
            if (checkIsAFavoriteMovie(movie)) {
                favoriteMoviesDAO.removeFavorite(movie.id)
                movie.isFavorite = false
            }
            true
        } catch (exception: Exception) {
            false
        }
    }

    override suspend fun checkIsAFavoriteMovie(movie: Movie): Boolean {
        val movieTitle = movie.title ?: return false
        val movieOriginalTitle = movie.originalTitle ?: return false
        return favoriteMoviesDAO.isThereAMovie(
            originalTitle = movieOriginalTitle,
            title = movieTitle
        ).any()
    }
}