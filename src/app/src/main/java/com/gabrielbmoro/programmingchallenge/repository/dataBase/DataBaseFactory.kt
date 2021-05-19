package com.gabrielbmoro.programmingchallenge.repository.dataBase

import androidx.room.Database
import androidx.room.RoomDatabase
import com.gabrielbmoro.programmingchallenge.domain.model.Movie

@Database(entities = [Movie::class], version = 1, exportSchema = false)
abstract class DataBaseFactory : RoomDatabase() {

    abstract fun favoriteMoviesDAO(): FavoriteMoviesDAO

}