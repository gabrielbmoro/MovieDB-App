package com.gabrielbmoro.moviedb.repository.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.gabrielbmoro.moviedb.repository.room.dto.FavoriteMovieDTO

@Database(entities = [FavoriteMovieDTO::class], version = 2, exportSchema = false)
abstract class DataBaseFactory : RoomDatabase() {

    abstract fun favoriteMoviesDAO(): FavoriteMoviesDAO

}