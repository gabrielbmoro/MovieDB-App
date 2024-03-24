package com.gabrielbmoro.moviedb.data.repository.datasources.database

import com.gabrielbmoro.moviedb.domain.entities.Movie

interface Database {

    suspend fun allFavoriteMovies(): List<Movie>

    suspend fun isThereAMovie(title: String): List<Movie>

    suspend fun removeFavorite(movieTitle: String)

    suspend fun saveFavorite(movie: Movie)
}
