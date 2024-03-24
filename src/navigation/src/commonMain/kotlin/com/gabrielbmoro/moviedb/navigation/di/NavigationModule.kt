package com.gabrielbmoro.moviedb.navigation.di

import com.gabrielbmoro.moviedb.navigation.MovieDBNavDestinations
import com.gabrielbmoro.moviedb.navigation.MovieDBNavDestinationsImpl
import org.koin.dsl.module

val navigationModule = module {
    factory<MovieDBNavDestinations> {
        MovieDBNavDestinationsImpl()
    }
}
