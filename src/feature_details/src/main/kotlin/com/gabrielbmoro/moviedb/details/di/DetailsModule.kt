package com.gabrielbmoro.moviedb.details.di

import com.gabrielbmoro.moviedb.details.ui.screens.details.DetailsScreenScreenModel
import org.koin.dsl.module

val featureDetailsModule = module {
    factory { params ->
        DetailsScreenScreenModel(
            favoriteMovieUseCase = get(),
            isFavoriteMovieUseCase = get(),
            getMovieDetailsUseCase = get(),
            movie = params.get()
        )
    }
}
