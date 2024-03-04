package com.gabrielbmoro.moviedb.domain

import androidx.paging.PagingData
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

    suspend fun favorite(movie: Movie)

    suspend fun unFavorite(movieTitle: String)

    suspend fun checkIsAFavoriteMovie(movieTitle: String): Boolean

    suspend fun getVideoStreams(movieId: Long): List<VideoStream>

    suspend fun getMovieDetail(movieId: Long): MovieDetail

    fun searchMovieBy(query: String): Flow<List<Movie>>
}
