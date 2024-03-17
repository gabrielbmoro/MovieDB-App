package com.gabrielbmoro.moviedb.movies.di

import com.gabrielbmoro.moviedb.movies.ui.screens.movies.MoviesViewModel
import org.koin.dsl.module

val featureMoviesModule = module {
    factory {
        MoviesViewModel(
            getPopularMoviesUseCase = get(),
            getNowPlayingMoviesUseCase = get(),
            getTopRatedMoviesUseCase = get(),
            getUpcomingMoviesUseCase = get(),
        )
    }
}
