package com.gabrielbmoro.moviedb.movies.di

import com.gabrielbmoro.moviedb.movies.ui.screens.movies.MoviesViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val featureMoviesModule = module {
    viewModel {
        MoviesViewModel(
            getPopularMoviesUseCase = get(),
            getNowPlayingMoviesUseCase = get(),
            getTopRatedMoviesUseCase = get(),
            getUpcomingMoviesUseCase = get(),
            resourcesProvider = get()
        )
    }
}
