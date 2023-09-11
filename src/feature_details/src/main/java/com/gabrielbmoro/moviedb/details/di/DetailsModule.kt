package com.gabrielbmoro.moviedb.details.di

import com.gabrielbmoro.moviedb.details.domain.usecases.FavoriteMovieUseCase
import com.gabrielbmoro.moviedb.details.domain.usecases.GetTrailersUseCase
import com.gabrielbmoro.moviedb.details.domain.usecases.IsFavoriteMovieUseCase
import com.gabrielbmoro.moviedb.repository.MoviesRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object DetailsModule {

    @Provides
    fun favoriteUseCase(repository: MoviesRepository): FavoriteMovieUseCase {
        return FavoriteMovieUseCase(repository)
    }

    @Provides
    fun getTrailersUseCase(repository: MoviesRepository): GetTrailersUseCase {
        return GetTrailersUseCase(repository)
    }

    @Provides
    fun isFavoriteMovieUseCase(repository: MoviesRepository): IsFavoriteMovieUseCase {
        return IsFavoriteMovieUseCase(repository)
    }
}