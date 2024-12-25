package com.gabrielbmoro.moviedb.movies.di

import com.gabrielbmoro.moviedb.movies.ui.screens.movies.MoviesViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.lazyModule

val featureMoviesModule = lazyModule {
    viewModel {
        MoviesViewModel(
            getPopularMoviesUseCase = get(),
            getUpcomingMoviesUseCase = get(),
            getNowPlayingMoviesUseCase = get(),
            getTopRatedMoviesUseCase = get(),
            ioDispatcher = Dispatchers.IO
        )
    }
}
