package com.gabrielbmoro.programmingchallenge.core.di

import com.gabrielbmoro.programmingchallenge.repository.MoviesRepository
import com.gabrielbmoro.programmingchallenge.repository.MoviesRepositoryImpl
import com.gabrielbmoro.programmingchallenge.repository.retrofit.ApiRepository
import com.gabrielbmoro.programmingchallenge.repository.room.FavoriteMoviesDAO
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    fun provideRepositoryInstance(
        apiRepository: ApiRepository,
        favoriteMoviesDAO: FavoriteMoviesDAO
    ): MoviesRepository {
        return MoviesRepositoryImpl(
            api = apiRepository,
            favoriteMoviesDAO = favoriteMoviesDAO
        )
    }
}