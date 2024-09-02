@file:Suppress("MatchingDeclarationName")

package com.gabrielbmoro.moviedb.data.providers

import androidx.room.ConstructedBy
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.RoomDatabaseConstructor
import com.gabrielbmoro.moviedb.data.repository.datasources.database.room.FavoriteMoviesDAO
import com.gabrielbmoro.moviedb.data.repository.datasources.database.room.dto.FavoriteMovieDTO

@Database(entities = [FavoriteMovieDTO::class], version = 2, exportSchema = false)
@ConstructedBy(AppDatabaseConstructor::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun favoriteMoviesDAO(): FavoriteMoviesDAO
}

@Suppress("TopLevelPropertyNaming")
internal const val dbFileName = "movieDBApp.db"

@Suppress("TopLevelPropertyNaming")
expect fun databaseInstance(): AppDatabase

expect object AppDatabaseConstructor : RoomDatabaseConstructor<AppDatabase>