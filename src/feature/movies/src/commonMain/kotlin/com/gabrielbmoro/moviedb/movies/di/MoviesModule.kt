package com.gabrielbmoro.moviedb.movies.di

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import org.koin.core.annotation.ComponentScan
import org.koin.core.annotation.Factory
import org.koin.core.annotation.Module
import org.koin.ksp.generated.module

val featureMoviesModule = MoviesModule().module

@Module
@ComponentScan("com.gabrielbmoro.moviedb.movies.ui")
class MoviesModule {

    @Factory
    fun ioDispatcher() : CoroutineDispatcher = Dispatchers.IO
}