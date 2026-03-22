package com.gabrielbmoro.moviedb.domain.di

import com.gabrielbmoro.moviedb.data.di.DataModule
import com.gabrielbmoro.moviedb.domain.MoviesRepository
import com.gabrielbmoro.moviedb.domain.MoviesRepositoryImpl
import com.gabrielbmoro.moviedb.domain.usecases.FavoriteMovieUseCase
import com.gabrielbmoro.moviedb.domain.usecases.FavoriteMovieUseCaseImpl
import com.gabrielbmoro.moviedb.domain.usecases.GetMovieDetailsUseCase
import com.gabrielbmoro.moviedb.domain.usecases.GetMovieDetailsUseCaseImpl
import org.koin.dsl.module
import org.koin.ksp.generated.module

val domainModule = module {
    includes(DataModule().module)

    factory<FavoriteMovieUseCase> {
        FavoriteMovieUseCaseImpl(
            repository = get(),
        )
    }

    factory<GetMovieDetailsUseCase> {
        GetMovieDetailsUseCaseImpl(
            repository = get(),
        )
    }

    single<MoviesRepository> {
        MoviesRepositoryImpl(
            dataRepository = get(),
        )
    }
}
