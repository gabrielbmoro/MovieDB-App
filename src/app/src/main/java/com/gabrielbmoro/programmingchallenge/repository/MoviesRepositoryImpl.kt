package com.gabrielbmoro.programmingchallenge.repository

import com.gabrielbmoro.programmingchallenge.domain.model.DataOrException
import com.gabrielbmoro.programmingchallenge.domain.model.Movie
import com.gabrielbmoro.programmingchallenge.domain.model.Page
import com.gabrielbmoro.programmingchallenge.repository.mappers.FavoriteMovieMapper
import com.gabrielbmoro.programmingchallenge.repository.mappers.MovieMapper
import com.gabrielbmoro.programmingchallenge.repository.mappers.PageMapper
import com.gabrielbmoro.programmingchallenge.repository.retrofit.ApiRepository
import com.gabrielbmoro.programmingchallenge.repository.room.FavoriteMoviesDAO

class MoviesRepositoryImpl(
    private val api: ApiRepository,
    private val favoriteMoviesDAO: FavoriteMoviesDAO,
    private val apiToken: String,
    private val favoriteMoviesMapper: FavoriteMovieMapper,
    private val pageMapper: PageMapper,
    private val movieMapper: MovieMapper,
) : MoviesRepository {

    override suspend fun getFavoriteMovies(): DataOrException<List<Movie>, Exception> {
        return try {
            DataOrException(
                data = favoriteMoviesDAO.allFavoriteMovies().map {
                    movieMapper.mapFavorite(it)
                },
                exception = null
            )
        } catch (exception: Exception) {
            DataOrException(data = null, exception = exception)
        }
    }

    override suspend fun getPopularMovies(pageNumber: Int): DataOrException<Page, Exception> {
        return try {
            val pageResponse = api.getPopularMovies(
                pageNumber = pageNumber,
                apiKey = apiToken
            )
            DataOrException(data = pageMapper.map(pageResponse), exception = null)
        } catch (exception: Exception) {
            DataOrException(exception = exception, data = null)
        }
    }

    override suspend fun getTopRatedMovies(pageNumber: Int): DataOrException<Page, Exception> {
        return try {
            val pageResponse = api.getTopRatedMovies(
                pageNumber = pageNumber,
                apiKey = apiToken
            )
            DataOrException(data = pageMapper.map(pageResponse), exception = null)
        } catch (exception: Exception) {
            DataOrException(data = null, exception = exception)
        }
    }

    override suspend fun doAsFavorite(movie: Movie): DataOrException<Boolean, Exception> {
        return try {
            val movieDTO = favoriteMoviesMapper.map(
                movie = movie
            )
            val isFavorite = checkIsAFavoriteMovie(movieDTO.title)
            if (isFavorite.data != null && isFavorite.data == false) {
                favoriteMoviesDAO.saveFavorite(movieDTO)
                DataOrException(data = true, exception = null)
            } else {
                DataOrException(data = false, exception = isFavorite.exception)
            }
        } catch (exception: Exception) {
            DataOrException(data = null, exception = exception)
        }
    }

    override suspend fun unFavorite(movieTitle: String): DataOrException<Boolean, Exception> {
        return try {
            favoriteMoviesDAO.removeFavorite(movieTitle)
            DataOrException(data = true, exception = null)
        } catch (exception: Exception) {
            DataOrException(data = null, exception = null)
        }
    }

    override suspend fun checkIsAFavoriteMovie(movieTitle: String): DataOrException<Boolean, Exception> {
        return try {
            val isFavorite = favoriteMoviesDAO.isThereAMovie(
                title = movieTitle
            ).any()
            DataOrException(data = isFavorite, exception = null)
        } catch (exception: Exception) {
            DataOrException(data = null, exception = exception)
        }
    }
}