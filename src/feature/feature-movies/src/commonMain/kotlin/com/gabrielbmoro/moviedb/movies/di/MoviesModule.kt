package com.gabrielbmoro.moviedb.movies.di

import MoviesHandler
import com.gabrielbmoro.moviedb.domain.di.DomainModule
import com.gabrielbmoro.moviedb.movies.ui.screens.movies.MoviesViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.lazyModule
import org.koin.ksp.generated.module

val featureMoviesModule = lazyModule {
    includes(DomainModule().module)

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
