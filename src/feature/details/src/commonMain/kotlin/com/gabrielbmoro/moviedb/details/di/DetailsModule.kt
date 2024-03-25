package com.gabrielbmoro.moviedb.details.di

import cafe.adriel.voyager.core.screen.Screen
import com.gabrielbmoro.moviedb.details.ui.screens.details.DetailsScreen
import com.gabrielbmoro.moviedb.details.ui.screens.details.DetailsScreenScreenModel
import com.gabrielbmoro.moviedb.platform.navigation.NavigationDestinations
import org.koin.core.qualifier.named
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

    factory<Screen>(named(NavigationDestinations.DETAILS)) { params ->
        DetailsScreen(params.get())
    }
}
