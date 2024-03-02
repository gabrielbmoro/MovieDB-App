package com.gabrielbmoro.moviedb.domain

import androidx.paging.PagingData
import com.gabrielbmoro.moviedb.domain.entities.DataOrException
import com.gabrielbmoro.moviedb.domain.entities.Movie
import com.gabrielbmoro.moviedb.domain.entities.MovieDetail
import com.gabrielbmoro.moviedb.domain.entities.VideoStream
import kotlinx.coroutines.flow.Flow

interface MoviesRepository {
    fun getFavoriteMovies(): Flow<List<Movie>>

    fun getPopularMovies(): Flow<PagingData<Movie>>

    fun getTopRatedMovies(): Flow<PagingData<Movie>>

    fun getUpcomingMovies(): Flow<PagingData<Movie>>

    fun getNowPlayingMovies(): Flow<PagingData<Movie>>

    suspend fun doAsFavorite(movie: Movie): DataOrException<Boolean, Exception>

    suspend fun unFavorite(movieTitle: String): DataOrException<Boolean, Exception>

    suspend fun checkIsAFavoriteMovie(movieTitle: String): DataOrException<Boolean, Exception>

    fun getVideoStreams(movieId: Long): Flow<List<VideoStream>>

    fun getMovieDetail(movieId: Long): Flow<MovieDetail>

    fun searchMovieBy(query: String): Flow<List<Movie>>
}
