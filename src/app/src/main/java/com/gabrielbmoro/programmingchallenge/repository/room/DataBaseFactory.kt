package com.gabrielbmoro.programmingchallenge.repository.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.gabrielbmoro.programmingchallenge.repository.room.entities.FavoriteMovieDTO

@Database(entities = [FavoriteMovieDTO::class], version = 1, exportSchema = false)
abstract class DataBaseFactory : RoomDatabase() {

    abstract fun favoriteMoviesDAO(): FavoriteMoviesDAO

}