package com.gabrielbmoro.moviedb.details.di

import cafe.adriel.voyager.core.screen.Screen
import com.gabrielbmoro.moviedb.details.ui.screens.details.DetailsScreen
import com.gabrielbmoro.moviedb.details.ui.screens.details.DetailsScreenViewModel
import com.gabrielbmoro.moviedb.platform.navigation.NavigationDestinations
import org.koin.core.qualifier.named
import org.koin.dsl.module

val featureDetailsModule =
    module {
        factory { params ->
            DetailsScreenViewModel(
                favoriteMovieUseCase = get(),
                isFavoriteMovieUseCase = get(),
                getMovieDetailsUseCase = get(),
                movieId = params.get()
            )
        }

        factory<Screen>(named(NavigationDestinations.DETAILS)) { params ->
            DetailsScreen(params.get())
        }
    }
