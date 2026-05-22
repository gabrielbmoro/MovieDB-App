package com.gabrielbmoro.moviedb.feature.details.di

import com.gabrielbmoro.moviedb.feature.details.ui.screens.details.DetailsViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.lazyModule

val featureDetailsModule = lazyModule {
    viewModel<DetailsViewModel> {
        DetailsViewModel(
            getMovieDetailsUseCase = get(),
            favoriteMovieUseCase = get(),
            repository = get(),
            ioDispatcher = Dispatchers.IO,
        )
    }
}
