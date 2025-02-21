package com.gabrielbmoro.moviedb.movies.di

import com.gabrielbmoro.moviedb.movies.domain.interactor.MoviesInteractor
import com.gabrielbmoro.moviedb.movies.domain.usecase.GetDefaultEmptyStateUseCase
import com.gabrielbmoro.moviedb.movies.domain.usecase.GetDefaultEmptyStateUseCaseImpl
import com.gabrielbmoro.moviedb.movies.domain.usecase.GetDefaultMenuItemsUseCase
import com.gabrielbmoro.moviedb.movies.domain.usecase.GetDefaultMenuItemsUseCaseImpl
import com.gabrielbmoro.moviedb.movies.domain.usecase.getmovies.GetMoviesFromFilterUseCase
import com.gabrielbmoro.moviedb.movies.domain.usecase.getmovies.GetMoviesFromFilterUseCaseImpl
import com.gabrielbmoro.moviedb.movies.ui.screens.movies.MoviesViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.lazyModule

val featureMoviesModule = lazyModule {

    factory<GetMoviesFromFilterUseCase> {
        GetMoviesFromFilterUseCaseImpl(get())
    }

    factory<GetDefaultEmptyStateUseCase> {
        GetDefaultEmptyStateUseCaseImpl(get())
    }

    factory<GetDefaultMenuItemsUseCase> {
        GetDefaultMenuItemsUseCaseImpl()
    }

    factory {
        MoviesInteractor(
            getMoviesFromFilter = get(),
            getDefaultEmptyState = get()
        )
    }

    viewModel {
        MoviesViewModel(
            ioDispatcher = Dispatchers.IO,
            loggerHelper = get(),
            interactor = get(),
        )
    }
}
