package com.gabrielbmoro.moviedb.data.providers

import com.gabrielbmoro.moviedb.data.repository.datasources.database.Database
import com.gabrielbmoro.moviedb.domain.entities.Movie

actual fun databaseInstance(): Database {
    return object : Database {
        override suspend fun allFavoriteMovies(): List<Movie> {
            return emptyList()
        }

        override suspend fun isThereAMovie(title: String): List<Movie> {
            return emptyList()
        }

        override suspend fun removeFavorite(movieTitle: String) = Unit

        override suspend fun saveFavorite(movie: Movie) = Unit
    }
}
