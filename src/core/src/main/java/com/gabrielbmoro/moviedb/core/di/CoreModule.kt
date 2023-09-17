package com.gabrielbmoro.moviedb.core.di

import android.content.Context
import com.gabrielbmoro.moviedb.core.providers.resources.AndroidResourcesProvider
import com.gabrielbmoro.moviedb.core.providers.resources.ResourcesProvider
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object CoreModule {
    @Provides
    fun bindsResourcesProvider(@ApplicationContext context: Context): ResourcesProvider {
        return AndroidResourcesProvider(context.resources)
    }
}
