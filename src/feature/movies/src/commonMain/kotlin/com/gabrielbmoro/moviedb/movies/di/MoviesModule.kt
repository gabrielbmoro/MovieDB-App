package com.gabrielbmoro.moviedb.movies.di

import MoviesHandler
import com.gabrielbmoro.moviedb.movies.ui.screens.movies.MoviesViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.lazyModule

val featureMoviesModule = lazyModule {

    factory {
        MoviesHandler(
            repository = get(),
        )
    }
    viewModel {
        MoviesViewModel(
            ioDispatcher = Dispatchers.IO,
            loggerHelper = get(),
            moviesHandler = get(),
        )
    }
}
