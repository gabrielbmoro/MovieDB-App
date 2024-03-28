package com.gabrielbmoro.moviedb.domain.di

import com.gabrielbmoro.moviedb.domain.usecases.FavoriteMovieUseCase
import com.gabrielbmoro.moviedb.domain.usecases.FavoriteMovieUseCaseImpl
import com.gabrielbmoro.moviedb.domain.usecases.GetFavoriteMoviesUseCase
import com.gabrielbmoro.moviedb.domain.usecases.GetFavoriteMoviesUseCaseImpl
import com.gabrielbmoro.moviedb.domain.usecases.GetMovieDetailsUseCase
import com.gabrielbmoro.moviedb.domain.usecases.GetMovieDetailsUseCaseImpl
import com.gabrielbmoro.moviedb.domain.usecases.GetNowPlayingMoviesUseCase
import com.gabrielbmoro.moviedb.domain.usecases.GetNowPlayingMoviesUseCaseImpl
import com.gabrielbmoro.moviedb.domain.usecases.GetPopularMoviesUseCase
import com.gabrielbmoro.moviedb.domain.usecases.GetPopularMoviesUseCaseImpl
import com.gabrielbmoro.moviedb.domain.usecases.GetTopRatedMoviesUseCase
import com.gabrielbmoro.moviedb.domain.usecases.GetTopRatedMoviesUseCaseImpl
import com.gabrielbmoro.moviedb.domain.usecases.GetUpcomingMoviesUseCase
import com.gabrielbmoro.moviedb.domain.usecases.GetUpcomingMoviesUseCaseImpl
import com.gabrielbmoro.moviedb.domain.usecases.IsFavoriteMovieUseCase
import com.gabrielbmoro.moviedb.domain.usecases.IsFavoriteMovieUseCaseImpl
import com.gabrielbmoro.moviedb.domain.usecases.SearchMovieUseCase
import com.gabrielbmoro.moviedb.domain.usecases.SearchMovieUseCaseImpl
import org.koin.dsl.module

val domainModule =
    module {
        factory<FavoriteMovieUseCase> {
            FavoriteMovieUseCaseImpl(
                repository = get(),
            )
        }

        factory<GetFavoriteMoviesUseCase> {
            GetFavoriteMoviesUseCaseImpl(
                repository = get(),
            )
        }

        factory<GetMovieDetailsUseCase> {
            GetMovieDetailsUseCaseImpl(
                repository = get(),
            )
        }

        factory<GetNowPlayingMoviesUseCase> {
            GetNowPlayingMoviesUseCaseImpl(
                repository = get(),
            )
        }

        factory<GetPopularMoviesUseCase> {
            GetPopularMoviesUseCaseImpl(
                repository = get(),
            )
        }

        factory<GetTopRatedMoviesUseCase> {
            GetTopRatedMoviesUseCaseImpl(
                repository = get(),
            )
        }

        factory<GetUpcomingMoviesUseCase> {
            GetUpcomingMoviesUseCaseImpl(
                repository = get(),
            )
        }

        factory<IsFavoriteMovieUseCase> {
            IsFavoriteMovieUseCaseImpl(
                repository = get(),
            )
        }

        factory<SearchMovieUseCase> {
            SearchMovieUseCaseImpl(
                repository = get(),
            )
        }
    }
