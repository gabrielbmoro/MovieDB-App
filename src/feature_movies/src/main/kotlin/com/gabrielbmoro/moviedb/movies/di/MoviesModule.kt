package com.gabrielbmoro.moviedb.movies.di

import com.gabrielbmoro.moviedb.movies.ui.screens.movies.MoviesScreenModel
import org.koin.dsl.module

val featureMoviesModule = module {
    factory {
        MoviesScreenModel(
            getPopularMoviesUseCase = get(),
            getNowPlayingMoviesUseCase = get(),
            getTopRatedMoviesUseCase = get(),
            getUpcomingMoviesUseCase = get(),
        )
    }
}
