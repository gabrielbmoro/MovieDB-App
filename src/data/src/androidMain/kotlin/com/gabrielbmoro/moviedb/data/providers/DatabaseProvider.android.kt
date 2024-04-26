package com.gabrielbmoro.moviedb.data.providers

import android.app.Application
import androidx.room.Room
import com.gabrielbmoro.moviedb.data.repository.datasources.database.Database
import com.gabrielbmoro.moviedb.data.repository.datasources.room.DataBaseFactory
import com.gabrielbmoro.moviedb.data.repository.datasources.room.MIGRATION_1_2
import com.gabrielbmoro.moviedb.data.repository.toFavoriteMovieDTO
import com.gabrielbmoro.moviedb.data.repository.toMovie
import com.gabrielbmoro.moviedb.domain.entities.Movie
import org.koin.mp.KoinPlatform

class DataBaseImpl(application: Application) : Database {
    private val favoriteMoviesDAO = Room.databaseBuilder(
        application,
        DataBaseFactory::class.java,
        "MovieDBAppDataBase",
    )
        .addMigrations(MIGRATION_1_2)
        .fallbackToDestructiveMigration()
        .build()
        .favoriteMoviesDAO()

    override suspend fun allFavoriteMovies(): List<Movie> {
        return favoriteMoviesDAO.allFavoriteMovies().map { it.toMovie() }
    }

    override suspend fun isThereAMovie(title: String): List<Movie> {
        return favoriteMoviesDAO.isThereAMovie(title).map { it.toMovie() }
    }

    override suspend fun removeFavorite(movieTitle: String) {
        favoriteMoviesDAO.removeFavorite(movieTitle)
    }

    override suspend fun saveFavorite(movie: Movie) {
        favoriteMoviesDAO.saveFavorite(movie.toFavoriteMovieDTO())
    }
}

actual fun databaseInstance(): Database {
    return DataBaseImpl(KoinPlatform.getKoin().get())
}
