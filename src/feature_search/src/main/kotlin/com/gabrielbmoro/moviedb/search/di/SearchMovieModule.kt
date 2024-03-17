package com.gabrielbmoro.moviedb.search.di

import com.gabrielbmoro.moviedb.search.ui.screens.search.SearchViewModel
import org.koin.dsl.module

val featureSearchMovieModule = module {
    factory {
        SearchViewModel(
            searchMovieUseCase = get()
        )
    }
}