package com.gabrielbmoro.programmingchallenge.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.gabrielbmoro.programmingchallenge.domain.model.DataOrException
import com.gabrielbmoro.programmingchallenge.domain.model.Movie
import com.gabrielbmoro.programmingchallenge.repository.mappers.FavoriteMovieMapper
import com.gabrielbmoro.programmingchallenge.repository.mappers.MovieMapper
import com.gabrielbmoro.programmingchallenge.repository.mappers.PageMapper
import com.gabrielbmoro.programmingchallenge.repository.paging.MoviesDataSource
import com.gabrielbmoro.programmingchallenge.repository.retrofit.ApiRepository
import com.gabrielbmoro.programmingchallenge.repository.room.FavoriteMoviesDAO
import kotlinx.coroutines.flow.Flow

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

    override fun getPopularMovies(): Flow<PagingData<Movie>> = getPagingData(
        MoviesDataSource.PagingType.POPULAR_MOVIES
    )

    override fun getTopRatedMovies(): Flow<PagingData<Movie>> =
        getPagingData(
            MoviesDataSource.PagingType.TOP_RATED_MOVIES
        )

    private fun getPagingData(pagingType: MoviesDataSource.PagingType): Flow<PagingData<Movie>> {
        val pagingDataFlow = Pager(
            pagingSourceFactory = {
                MoviesDataSource(
                    apiKey = apiToken,
                    api = api,
                    pagingType = pagingType
                )
            },
            initialKey = 1,
            config = PagingConfig(
                pageSize = PAGE_SIZE,
                maxSize = MAX_SIZE
            )
        ).flow
        return pageMapper.map(pagingDataFlow)
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

    companion object {
        private const val PAGE_SIZE = 20
        private const val MAX_SIZE = 500
    }
}