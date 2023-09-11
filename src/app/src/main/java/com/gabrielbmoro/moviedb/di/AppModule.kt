package com.gabrielbmoro.moviedb.di

import com.gabrielbmoro.moviedb.BuildConfig
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Named

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Named("api_token")
    fun apiToken(): String {
        return BuildConfig.API_TOKEN
    }
}