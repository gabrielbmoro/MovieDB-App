package com.gabrielbmoro.moviedb.core.di

import com.gabrielbmoro.moviedb.core.providers.resources.AndroidResourcesProvider
import com.gabrielbmoro.moviedb.core.providers.resources.ResourcesProvider
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.bind
import org.koin.dsl.module

val coreModule = module {
    factory { AndroidResourcesProvider(androidApplication().resources) } bind ResourcesProvider::class
}
