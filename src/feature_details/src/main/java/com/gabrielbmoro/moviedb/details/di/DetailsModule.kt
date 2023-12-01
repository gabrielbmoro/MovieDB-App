package com.gabrielbmoro.moviedb.details.di

import com.gabrielbmoro.moviedb.details.domain.usecases.FavoriteMovieUseCase
import com.gabrielbmoro.moviedb.details.domain.usecases.FavoriteMovieUseCaseImpl
import com.gabrielbmoro.moviedb.details.domain.usecases.GetTrailersUseCase
import com.gabrielbmoro.moviedb.details.domain.usecases.GetTrailersUseCaseImpl
import com.gabrielbmoro.moviedb.details.domain.usecases.IsFavoriteMovieUseCase
import com.gabrielbmoro.moviedb.details.domain.usecases.IsFavoriteMovieUseCaseImpl
import com.gabrielbmoro.moviedb.repository.MoviesRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
object DetailsModule {

    @Provides
    fun favoriteUseCase(repository: MoviesRepository): FavoriteMovieUseCase {
        return FavoriteMovieUseCaseImpl(repository)
    }

    @Provides
    fun getTrailersUseCase(repository: MoviesRepository): GetTrailersUseCase {
        return GetTrailersUseCaseImpl(repository)
    }

    @Provides
    fun isFavoriteMovieUseCase(repository: MoviesRepository): IsFavoriteMovieUseCase {
        return IsFavoriteMovieUseCaseImpl(repository)
    }
}
