package com.gabrielbmoro.moviedb.search.di

import com.gabrielbmoro.moviedb.search.ui.screens.search.SearchScreenModel
import org.koin.dsl.module

val featureSearchMovieModule = module {
    factory {
        SearchScreenModel(
            searchMovieUseCase = get()
        )
    }
}