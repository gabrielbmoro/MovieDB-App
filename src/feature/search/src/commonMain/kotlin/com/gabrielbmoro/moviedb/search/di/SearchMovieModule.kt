package com.gabrielbmoro.moviedb.search.di

import com.gabrielbmoro.moviedb.search.ui.screens.search.SearchViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import org.koin.compose.viewmodel.dsl.viewModel
import org.koin.dsl.lazyModule

val featureSearchMovieModule = lazyModule {
    viewModel {
        SearchViewModel(
            searchMovieUseCase = get(),
            ioCoroutinesDispatcher = Dispatchers.IO
        )
    }
}
