package com.gabrielbmoro.programmingchallenge.repository

import androidx.paging.PagingData
import com.gabrielbmoro.programmingchallenge.domain.model.DataOrException
import com.gabrielbmoro.programmingchallenge.domain.model.Movie
import com.gabrielbmoro.programmingchallenge.domain.model.Page
import kotlinx.coroutines.flow.Flow

interface MoviesRepository {
    suspend fun getFavoriteMovies(): DataOrException<List<Movie>, Exception>

    fun getPopularMovies(): Flow<PagingData<Movie>>

    fun getTopRatedMovies(): Flow<PagingData<Movie>>

    suspend fun doAsFavorite(movie: Movie): DataOrException<Boolean, Exception>

    suspend fun unFavorite(movieTitle: String): DataOrException<Boolean, Exception>

    suspend fun checkIsAFavoriteMovie(movieTitle: String): DataOrException<Boolean, Exception>
}