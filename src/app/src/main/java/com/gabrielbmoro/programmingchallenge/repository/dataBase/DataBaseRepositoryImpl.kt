package com.gabrielbmoro.programmingchallenge.repository.dataBase

import androidx.paging.DataSource
import com.gabrielbmoro.programmingchallenge.domain.model.Movie
import com.gabrielbmoro.programmingchallenge.repository.MoviesRepository
import com.gabrielbmoro.programmingchallenge.repository.api.response.PageResponse

class DataBaseRepositoryImpl(private val favoriteDao: FavoriteMoviesDAO) : MoviesRepository {

    override fun getFavoriteMovies(): DataSource.Factory<Int, Movie> {
        return favoriteDao.allFavoriteMovies()
    }

    override suspend fun getPopularMovies(pageNumber: Int): PageResponse? = null

    override suspend fun getTopRatedMovies(pageNumber: Int): PageResponse? = null

    override suspend fun doAsFavorite(movie: Movie): Boolean {
        return try {
            favoriteDao.saveFavorite(movie.also { it.isFavorite = true })
            true
        } catch (exception: Exception) {
            false
        }
    }

    override suspend fun unFavorite(movie: Movie): Boolean {
        return try {
            favoriteDao.removeFavorite(movie.id)
            true
        } catch (exception: Exception) {
            false
        }
    }

    override suspend fun checkIsAFavoriteMovie(movie: Movie): Boolean {
        return favoriteDao.isThereAMovie(
                title = movie.title,
                originalTitle = movie.originalTitle
        ).isNotEmpty()
    }

}