package com.gabrielbmoro.moviedb.movies.di

import cafe.adriel.voyager.core.screen.Screen
import com.gabrielbmoro.moviedb.movies.ui.screens.movies.MoviesScreen
import com.gabrielbmoro.moviedb.movies.ui.screens.movies.MoviesScreenModel
import com.gabrielbmoro.moviedb.platform.navigation.NavigationDestinations
import org.koin.core.qualifier.named
import org.koin.dsl.module

val featureMoviesModule =
    module {
        factory {
            MoviesScreenModel(
                getPopularMoviesUseCase = get(),
                getNowPlayingMoviesUseCase = get(),
                getTopRatedMoviesUseCase = get(),
                getUpcomingMoviesUseCase = get()
            )
        }

        factory<Screen>(named(NavigationDestinations.MOVIES)) {
            MoviesScreen()
        }
    }
