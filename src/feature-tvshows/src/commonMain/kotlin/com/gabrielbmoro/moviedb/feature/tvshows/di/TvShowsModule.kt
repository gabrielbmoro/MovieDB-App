package com.gabrielbmoro.moviedb.feature.tvshows.di

import com.gabrielbmoro.moviedb.feature.tvshows.components.TvShowsHandler
import com.gabrielbmoro.moviedb.feature.tvshows.ui.screens.tvshows.TvShowsViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.lazyModule

val featureTvShowsModule = lazyModule {
    factory {
        TvShowsHandler(
            repository = get(),
        )
    }
    viewModel {
        TvShowsViewModel(
            ioDispatcher = Dispatchers.IO,
            loggerHelper = get(),
            tvShowsHandler = get(),
        )
    }
}
