package com.gabrielbmoro.programmingchallenge.repository

import com.gabrielbmoro.programmingchallenge.domain.model.DataOrException
import com.gabrielbmoro.programmingchallenge.domain.model.Movie
import com.gabrielbmoro.programmingchallenge.domain.model.Page
import com.gabrielbmoro.programmingchallenge.repository.room.dto.FavoriteMovieDTO

interface MoviesRepository {
    suspend fun getFavoriteMovies(): DataOrException<List<Movie>, Exception>

    suspend fun getPopularMovies(pageNumber: Int): DataOrException<Page, Exception>

    suspend fun getTopRatedMovies(pageNumber: Int): DataOrException<Page, Exception>

    suspend fun doAsFavorite(movie: Movie): DataOrException<Boolean, Exception>

    suspend fun unFavorite(movieTitle: String): DataOrException<Boolean, Exception>

    suspend fun checkIsAFavoriteMovie(movie: FavoriteMovieDTO): DataOrException<Boolean, Exception>
}