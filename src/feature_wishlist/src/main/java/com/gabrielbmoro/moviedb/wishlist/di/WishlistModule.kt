package com.gabrielbmoro.moviedb.wishlist.di

import com.gabrielbmoro.moviedb.repository.MoviesRepository
import com.gabrielbmoro.moviedb.wishlist.domain.usecases.GetFavoriteMoviesUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object WishlistModule {

    @Provides
    fun getFavoriteMoviesUseCase(repository: MoviesRepository): GetFavoriteMoviesUseCase {
        return GetFavoriteMoviesUseCase(repository)
    }
}