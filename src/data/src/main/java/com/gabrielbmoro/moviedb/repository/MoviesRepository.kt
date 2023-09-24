package com.gabrielbmoro.moviedb.repository

import androidx.paging.PagingData
import com.gabrielbmoro.moviedb.domain.model.DataOrException
import com.gabrielbmoro.moviedb.repository.model.Movie
import com.gabrielbmoro.moviedb.repository.model.MovieDetail
import com.gabrielbmoro.moviedb.repository.model.VideoStream
import kotlinx.coroutines.flow.Flow

interface MoviesRepository {
    suspend fun getFavoriteMovies(): DataOrException<List<Movie>, Exception>

    fun getPopularMovies(): Flow<PagingData<Movie>>

    fun getTopRatedMovies(): Flow<PagingData<Movie>>

    fun getUpcomingMovies(): Flow<PagingData<Movie>>

    fun getNowPlayingMovies(): Flow<PagingData<Movie>>

    suspend fun doAsFavorite(movie: Movie): DataOrException<Boolean, Exception>

    suspend fun unFavorite(movieTitle: String): DataOrException<Boolean, Exception>

    suspend fun checkIsAFavoriteMovie(movieTitle: String): DataOrException<Boolean, Exception>

    fun getVideoStreams(movieId: Long): Flow<List<VideoStream>>

    fun getMovieDetail(movieId: Long): Flow<MovieDetail>
}
