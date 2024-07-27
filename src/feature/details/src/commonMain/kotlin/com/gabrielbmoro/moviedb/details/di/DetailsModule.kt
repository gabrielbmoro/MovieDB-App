package com.gabrielbmoro.moviedb.details.di

import com.gabrielbmoro.moviedb.details.ui.screens.details.DetailsViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import org.koin.compose.viewmodel.dsl.viewModel
import org.koin.core.annotation.ComponentScan
import org.koin.core.annotation.Factory
import org.koin.core.annotation.Module
import org.koin.dsl.module
import org.koin.ksp.generated.module

val featureDetailsModule = DetailsModule().module.apply {
    includes(
        module {
            viewModel<DetailsViewModel> {
                DetailsViewModel(
                    getMovieDetailsUseCase = get(),
                    favoriteMovieUseCase = get(),
                    isFavoriteMovieUseCase = get(),
                    ioDispatcher = Dispatchers.IO
                )
            }
        }
    )
}

@Module
@ComponentScan("com.gabrielbmoro.moviedb.details.ui")
class DetailsModule {
    @Factory
    fun ioDispatcher(): CoroutineDispatcher = Dispatchers.IO
}