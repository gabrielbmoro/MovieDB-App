package com.gabrielbmoro.moviedb.movies.di

import org.koin.core.annotation.ComponentScan
import org.koin.core.annotation.Module
import org.koin.ksp.generated.module

val featureMoviesModule = MoviesModule().module

@Module
@ComponentScan("com.gabrielbmoro.moviedb.movies.ui")
class MoviesModule