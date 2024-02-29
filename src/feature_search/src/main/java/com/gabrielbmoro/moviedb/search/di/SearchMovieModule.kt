package com.gabrielbmoro.moviedb.search.di

import com.gabrielbmoro.moviedb.search.domain.SearchMovieUseCase
import com.gabrielbmoro.moviedb.search.domain.SearchMovieUseCaseImpl
import com.gabrielbmoro.moviedb.search.ui.screens.search.SearchViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.bind
import org.koin.dsl.module

val featureSearchMovieModule = module {
    viewModel {
        SearchViewModel(
            searchMovieUseCase = get()
        )
    }

    factory { SearchMovieUseCaseImpl(repository = get()) }.bind(
        SearchMovieUseCase::class
    )
}