package com.gabrielbmoro.moviedb.details.di

import com.gabrielbmoro.moviedb.details.ui.screens.details.DetailsScreenViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val featureDetailsModule = module {
    viewModel { params ->
        DetailsScreenViewModel(
            favoriteMovieUseCase = get(),
            isFavoriteMovieUseCase = get(),
            getMovieDetailsUseCase = get(),
            movie = params.get()
        )
    }
}
