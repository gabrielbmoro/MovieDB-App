package com.gabrielbmoro.moviedb.search.di

import cafe.adriel.voyager.core.screen.Screen
import com.gabrielbmoro.moviedb.platform.navigation.NavigationDestinations
import com.gabrielbmoro.moviedb.search.ui.screens.search.SearchScreen
import com.gabrielbmoro.moviedb.search.ui.screens.search.SearchViewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

val featureSearchMovieModule =
    module {
        factory {
            SearchViewModel(
                query = it.getOrNull(),
                searchMovieUseCase = get()
            )
        }

        factory<Screen>(named(NavigationDestinations.SEARCH)) {
            SearchScreen(query = it.getOrNull())
        }
    }
