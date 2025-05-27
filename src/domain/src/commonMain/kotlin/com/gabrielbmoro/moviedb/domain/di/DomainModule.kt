package com.gabrielbmoro.moviedb.domain.di

import com.gabrielbmoro.moviedb.data.di.dataModule
import com.gabrielbmoro.moviedb.domain.MoviesRepository
import com.gabrielbmoro.moviedb.domain.MoviesRepositoryImpl
import com.gabrielbmoro.moviedb.domain.usecases.FavoriteMovieUseCase
import com.gabrielbmoro.moviedb.domain.usecases.FavoriteMovieUseCaseImpl
import com.gabrielbmoro.moviedb.domain.usecases.GetMovieDetailsUseCase
import com.gabrielbmoro.moviedb.domain.usecases.GetMovieDetailsUseCaseImpl
import org.koin.dsl.module

val domainModule = module {
    includes(dataModule)

    factory<FavoriteMovieUseCase> {
        FavoriteMovieUseCaseImpl(
            repository = get()
        )
    }

    factory<GetMovieDetailsUseCase> {
        GetMovieDetailsUseCaseImpl(
            repository = get()
        )
    }

    single<MoviesRepository> {
        MoviesRepositoryImpl(
            dataRepository = get()
        )
    }
}