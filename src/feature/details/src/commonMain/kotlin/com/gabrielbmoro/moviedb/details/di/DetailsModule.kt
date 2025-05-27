package com.gabrielbmoro.moviedb.details.di

import com.gabrielbmoro.moviedb.details.ui.screens.details.DetailsViewModel
import com.gabrielbmoro.moviedb.domain.di.domainModule
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.lazyModule

val featureDetailsModule = lazyModule {
    includes(domainModule)

    viewModel<DetailsViewModel> {
        DetailsViewModel(
            getMovieDetailsUseCase = get(),
            favoriteMovieUseCase = get(),
            repository = get(),
            ioDispatcher = Dispatchers.IO,
        )
    }
}
