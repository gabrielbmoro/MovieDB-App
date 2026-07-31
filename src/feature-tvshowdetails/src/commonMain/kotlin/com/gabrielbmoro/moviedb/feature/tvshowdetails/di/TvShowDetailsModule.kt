package com.gabrielbmoro.moviedb.feature.tvshowdetails.di

import com.gabrielbmoro.moviedb.feature.tvshowdetails.ui.screens.details.TvShowDetailsViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.lazyModule

val featureTvShowDetailsModule = lazyModule {
    viewModel<TvShowDetailsViewModel> {
        TvShowDetailsViewModel(
            getTvShowDetailsUseCase = get(),
            ioDispatcher = Dispatchers.IO,
            loggerHelper = get(),
        )
    }
}
