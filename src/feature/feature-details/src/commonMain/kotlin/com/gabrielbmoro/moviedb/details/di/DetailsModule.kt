package com.gabrielbmoro.moviedb.details.di

import com.gabrielbmoro.moviedb.details.ui.screens.details.DetailsViewModel
import com.gabrielbmoro.moviedb.domain.di.DomainModule
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.lazyModule
import org.koin.ksp.generated.module

val featureDetailsModule = lazyModule {
    includes(DomainModule().module)

    viewModel<DetailsViewModel> {
        DetailsViewModel(
            getMovieDetailsUseCase = get(),
            favoriteMovieUseCase = get(),
            repository = get(),
            ioDispatcher = Dispatchers.IO,
        )
    }
}
