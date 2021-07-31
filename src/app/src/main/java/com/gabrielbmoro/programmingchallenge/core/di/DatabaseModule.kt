package com.gabrielbmoro.programmingchallenge.core.di

import android.content.Context
import androidx.room.Room
import com.gabrielbmoro.programmingchallenge.repository.room.DataBaseFactory
import com.gabrielbmoro.programmingchallenge.repository.room.FavoriteMoviesDAO
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    fun favoriteDao(
        @ApplicationContext context: Context,
    ): FavoriteMoviesDAO {
        return Room.databaseBuilder(
            context,
            DataBaseFactory::class.java,
            "MovieDBAppDataBase"
        ).build().favoriteMoviesDAO()
    }
}