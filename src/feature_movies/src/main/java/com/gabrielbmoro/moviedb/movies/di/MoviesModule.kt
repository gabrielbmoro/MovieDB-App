package com.gabrielbmoro.moviedb.movies.di

import com.gabrielbmoro.moviedb.movies.domain.usecases.GetNowPlayingMoviesUseCase
import com.gabrielbmoro.moviedb.movies.domain.usecases.GetPopularMoviesUseCase
import com.gabrielbmoro.moviedb.movies.domain.usecases.GetTopRatedMoviesUseCase
import com.gabrielbmoro.moviedb.movies.domain.usecases.GetUpcomingMoviesUseCase
import com.gabrielbmoro.moviedb.movies.ui.screens.movies.MoviesViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

object MoviesModule {
    val module = module {
        viewModel {
            MoviesViewModel(
                get(),
                get(),
                get(),
                get(),
                get()
            )
        }

        single { GetNowPlayingMoviesUseCase(get()) }

        single { GetPopularMoviesUseCase(get()) }

        single { GetTopRatedMoviesUseCase(get()) }

        single { GetUpcomingMoviesUseCase(get()) }
    }
}