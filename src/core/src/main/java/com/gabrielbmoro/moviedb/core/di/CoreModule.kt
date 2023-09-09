package com.gabrielbmoro.moviedb.core.di

import com.gabrielbmoro.moviedb.core.providers.resources.AndroidResourcesProvider
import com.gabrielbmoro.moviedb.core.providers.resources.ResourcesProvider
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

object CoreModule {
    val providersModule = module {
        factory<ResourcesProvider> {
            AndroidResourcesProvider(androidContext().resources)
        }
    }
}