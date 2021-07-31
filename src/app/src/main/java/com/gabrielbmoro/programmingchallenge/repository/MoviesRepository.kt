package com.gabrielbmoro.programmingchallenge.repository

import com.gabrielbmoro.programmingchallenge.repository.retrofit.responses.PageResponse
import com.gabrielbmoro.programmingchallenge.repository.room.entities.FavoriteMovieDTO

interface MoviesRepository {
    suspend fun getFavoriteMovies(): List<FavoriteMovieDTO>

    suspend fun getPopularMovies(pageNumber: Int): PageResponse

    suspend fun getTopRatedMovies(pageNumber: Int): PageResponse

    suspend fun doAsFavorite(movie: FavoriteMovieDTO): Boolean

    suspend fun unFavorite(movieTitle: String): Boolean

    suspend fun checkIsAFavoriteMovie(movie: FavoriteMovieDTO): Boolean
}