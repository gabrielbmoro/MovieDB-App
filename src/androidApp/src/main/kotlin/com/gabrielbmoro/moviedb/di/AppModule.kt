package com.gabrielbmoro.moviedb.di

import com.gabrielbmoro.moviedb.BuildConfig
import com.gabrielbmoro.moviedb.core.ui.navigation.MovieDBNavDestinations
import com.gabrielbmoro.moviedb.navigation.MovieDBNavDestinationsImpl
import org.koin.core.qualifier.named
import org.koin.dsl.module

val appModule = module {
    single(named("api_token")) {
        BuildConfig.API_TOKEN
    }

    factory<MovieDBNavDestinations> {
        MovieDBNavDestinationsImpl()
    }
}
