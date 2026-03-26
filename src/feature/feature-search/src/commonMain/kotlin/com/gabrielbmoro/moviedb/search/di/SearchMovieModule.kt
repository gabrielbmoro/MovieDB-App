package com.gabrielbmoro.moviedb.search.di

import com.gabrielbmoro.moviedb.domain.di.DomainModule
import com.gabrielbmoro.moviedb.search.ui.screens.search.SearchViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.lazyModule
import org.koin.ksp.generated.module

val featureSearchMovieModule = lazyModule {
    includes(DomainModule().module)

    viewModel { param ->
        SearchViewModel(
            query = param.get(),
            repository = get(),
            ioCoroutinesDispatcher = Dispatchers.IO,
        )
    }
}
