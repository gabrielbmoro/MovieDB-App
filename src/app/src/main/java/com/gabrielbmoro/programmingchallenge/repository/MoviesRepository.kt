package com.gabrielbmoro.programmingchallenge.repository

import androidx.paging.DataSource
import com.gabrielbmoro.programmingchallenge.domain.model.Movie
import com.gabrielbmoro.programmingchallenge.repository.api.response.PageResponse

interface MoviesRepository {

    fun getFavoriteMovies(): DataSource.Factory<Int, Movie>?

    suspend fun getPopularMovies(pageNumber: Int): PageResponse?

    suspend fun getTopRatedMovies(pageNumber: Int): PageResponse?

    suspend fun doAsFavorite(movie: Movie): Boolean

    suspend fun unFavorite(movie: Movie): Boolean

    suspend fun checkIsAFavoriteMovie(movie: Movie): Boolean
}