package com.gabrielbmoro.moviedb.data.di

import androidx.room.Room
import com.gabrielbmoro.moviedb.data.repository.datasources.room.DataBaseFactory
import com.gabrielbmoro.moviedb.data.repository.datasources.room.MIGRATION_1_2
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

val androidDataModule =
    module {
        single {
            Room.databaseBuilder(
                androidApplication(),
                DataBaseFactory::class.java,
                "MovieDBAppDataBase",
            )
                .addMigrations(MIGRATION_1_2)
                .fallbackToDestructiveMigration()
                .build()
                .favoriteMoviesDAO()
        }
    }
