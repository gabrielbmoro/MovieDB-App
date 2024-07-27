package com.gabrielbmoro.moviedb.details.di

import com.gabrielbmoro.moviedb.details.ui.screens.details.DetailsViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import org.koin.compose.viewmodel.dsl.viewModel
import org.koin.dsl.module

val featureDetailsModule = module {
    viewModel<DetailsViewModel> {
        DetailsViewModel(
            getMovieDetailsUseCase = get(),
            favoriteMovieUseCase = get(),
            isFavoriteMovieUseCase = get(),
            ioDispatcher = Dispatchers.IO
        )
    }
}
