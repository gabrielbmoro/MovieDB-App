package com.gabrielbmoro.programmingchallenge.repository

import com.gabrielbmoro.programmingchallenge.repository.entities.Movie
import com.gabrielbmoro.programmingchallenge.repository.entities.PageMovies

interface MoviesRepository {
    suspend fun getFavoriteMovies(): List<Movie>

    suspend fun getPopularMovies(pageNumber: Int): PageMovies

    suspend fun getTopRatedMovies(pageNumber: Int): PageMovies

    suspend fun doAsFavorite(movie: Movie): Boolean

    suspend fun unFavorite(movie: Movie): Boolean

    suspend fun checkIsAFavoriteMovie(movie: Movie): Boolean
}