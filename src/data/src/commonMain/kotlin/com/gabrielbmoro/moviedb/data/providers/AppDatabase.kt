package com.gabrielbmoro.moviedb.data.providers

import androidx.room.Database
import androidx.room.RoomDatabase
import com.gabrielbmoro.moviedb.data.repository.datasources.database.room.FavoriteMoviesDAO
import com.gabrielbmoro.moviedb.data.repository.datasources.database.room.dto.FavoriteMovieDTO

@Database(entities = [FavoriteMovieDTO::class], version = 2, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun favoriteMoviesDAO(): FavoriteMoviesDAO
}

internal const val dbFileName = "fruits.db"