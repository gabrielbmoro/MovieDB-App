package com.gabrielbmoro.moviedb.movies.di

import com.gabrielbmoro.moviedb.movies.domain.usecases.GetNowPlayingMoviesUseCase
import com.gabrielbmoro.moviedb.movies.domain.usecases.GetNowPlayingMoviesUseCaseImpl
import com.gabrielbmoro.moviedb.movies.domain.usecases.GetPopularMoviesUseCase
import com.gabrielbmoro.moviedb.movies.domain.usecases.GetPopularMoviesUseCaseImpl
import com.gabrielbmoro.moviedb.movies.domain.usecases.GetTopRatedMoviesUseCase
import com.gabrielbmoro.moviedb.movies.domain.usecases.GetTopRatedMoviesUseCaseImpl
import com.gabrielbmoro.moviedb.movies.domain.usecases.GetUpcomingMoviesUseCase
import com.gabrielbmoro.moviedb.movies.domain.usecases.GetUpcomingMoviesUseCaseImpl
import com.gabrielbmoro.moviedb.movies.ui.screens.movies.MoviesViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.bind
import org.koin.dsl.module

val featureMoviesModule = module {
    viewModel {
        MoviesViewModel(
            getPopularMoviesUseCase = get(),
            getNowPlayingMoviesUseCase = get(),
            getTopRatedMoviesUseCase = get(),
            getUpcomingMoviesUseCase = get(),
            resourcesProvider = get()
        )
    }

    factory {
        GetPopularMoviesUseCaseImpl(repository = get())
    }.bind(GetPopularMoviesUseCase::class)

    factory {
        GetNowPlayingMoviesUseCaseImpl(repository = get())
    }.bind(GetNowPlayingMoviesUseCase::class)

    factory {
        GetTopRatedMoviesUseCaseImpl(repository = get())
    }.bind(GetTopRatedMoviesUseCase::class)

    factory {
        GetUpcomingMoviesUseCaseImpl(repository = get())
    }.bind(GetUpcomingMoviesUseCase::class)
}
