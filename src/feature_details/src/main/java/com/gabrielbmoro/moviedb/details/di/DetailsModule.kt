package com.gabrielbmoro.moviedb.details.di

import com.gabrielbmoro.moviedb.details.domain.usecases.FavoriteMovieUseCase
import com.gabrielbmoro.moviedb.details.domain.usecases.GetTrailersUseCase
import com.gabrielbmoro.moviedb.details.domain.usecases.IsFavoriteMovieUseCase
import com.gabrielbmoro.moviedb.details.ui.screens.details.DetailsScreenViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

object DetailsModule {
    val module = module {
        viewModel {
            DetailsScreenViewModel(
                get(),
                get(),
                get(),
                get()
            )
        }

        single {
            FavoriteMovieUseCase(get())
        }

        single {
            GetTrailersUseCase(get())
        }

        single {
            IsFavoriteMovieUseCase(get())
        }
    }
}