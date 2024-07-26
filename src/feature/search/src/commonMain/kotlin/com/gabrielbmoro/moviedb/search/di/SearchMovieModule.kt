package com.gabrielbmoro.moviedb.search.di

import org.koin.core.annotation.ComponentScan
import org.koin.core.annotation.Module
import org.koin.ksp.generated.module

val featureSearchMovieModule = SearchModule().module

@Module
@ComponentScan("com.gabrielbmoro.moviedb.search.ui")
class SearchModule
