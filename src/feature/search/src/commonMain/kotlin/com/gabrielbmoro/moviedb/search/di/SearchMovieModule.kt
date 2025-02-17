package com.gabrielbmoro.moviedb.search.di

import com.gabrielbmoro.moviedb.search.ui.screens.search.SearchViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.lazyModule

val featureSearchMovieModule = lazyModule {
    viewModel { param ->
        SearchViewModel(
            query = param.get(),
            searchMovieUseCase = get(),
            ioCoroutinesDispatcher = Dispatchers.IO
        )
    }
}
