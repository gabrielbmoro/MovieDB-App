package com.gabrielbmoro.moviedb.data.repository.datasources.room

import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

val MIGRATION_1_2 =
    object : Migration(1, 2) {
        override fun migrate(database: SupportSQLiteDatabase) {
            val sql = "ALTER TABLE `favorite_movies` ADD COLUMN `movieId` INTEGER NOT NULL DEFAULT 0"
            database.execSQL(sql)
        }
    }
