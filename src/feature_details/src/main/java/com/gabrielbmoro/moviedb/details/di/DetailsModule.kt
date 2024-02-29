package com.gabrielbmoro.moviedb.details.di

import com.gabrielbmoro.moviedb.details.domain.usecases.FavoriteMovieUseCase
import com.gabrielbmoro.moviedb.details.domain.usecases.FavoriteMovieUseCaseImpl
import com.gabrielbmoro.moviedb.details.domain.usecases.GetMovieDetailsUseCase
import com.gabrielbmoro.moviedb.details.domain.usecases.GetMovieDetailsUseCaseImpl
import com.gabrielbmoro.moviedb.details.domain.usecases.GetTrailersUseCase
import com.gabrielbmoro.moviedb.details.domain.usecases.GetTrailersUseCaseImpl
import com.gabrielbmoro.moviedb.details.domain.usecases.IsFavoriteMovieUseCase
import com.gabrielbmoro.moviedb.details.domain.usecases.IsFavoriteMovieUseCaseImpl
import com.gabrielbmoro.moviedb.details.ui.screens.details.DetailsScreenViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.bind
import org.koin.dsl.module

val featureDetailsModule = module {
    viewModel {
        DetailsScreenViewModel(
            favoriteMovieUseCase = get(),
            isFavoriteMovieUseCase = get(),
            getMovieDetailsUseCase = get()
        )
    }

    factory { FavoriteMovieUseCaseImpl(repository = get()) }.bind(
        FavoriteMovieUseCase::class
    )

    factory { IsFavoriteMovieUseCaseImpl(repository = get()) }.bind(
        IsFavoriteMovieUseCase::class
    )

    factory { GetMovieDetailsUseCaseImpl(getTrailersUseCase = get(), repository = get()) }.bind(
        GetMovieDetailsUseCase::class
    )

    factory {
        GetTrailersUseCaseImpl(repository = get())
    }.bind(GetTrailersUseCase::class)
}
