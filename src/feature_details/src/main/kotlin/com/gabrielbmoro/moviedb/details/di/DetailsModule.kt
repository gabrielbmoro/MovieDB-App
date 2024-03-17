package com.gabrielbmoro.moviedb.details.di

import com.gabrielbmoro.moviedb.details.ui.screens.details.DetailsScreenViewModel
import org.koin.dsl.module

val featureDetailsModule = module {
    factory { params ->
        DetailsScreenViewModel(
            favoriteMovieUseCase = get(),
            isFavoriteMovieUseCase = get(),
            getMovieDetailsUseCase = get(),
            movie = params.get()
        )
    }
}
