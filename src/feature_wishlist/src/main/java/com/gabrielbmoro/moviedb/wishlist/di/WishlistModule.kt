package com.gabrielbmoro.moviedb.wishlist.di

import com.gabrielbmoro.moviedb.repository.MoviesRepository
import com.gabrielbmoro.moviedb.wishlist.domain.usecases.DeleteMovieUseCase
import com.gabrielbmoro.moviedb.wishlist.domain.usecases.DeleteMovieUseCaseImpl
import com.gabrielbmoro.moviedb.wishlist.domain.usecases.GetFavoriteMoviesUseCase
import com.gabrielbmoro.moviedb.wishlist.domain.usecases.GetFavoriteMoviesUseCaseImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
object WishlistModule {

    @Provides
    @ViewModelScoped
    fun getFavoriteMoviesUseCase(repository: MoviesRepository): GetFavoriteMoviesUseCase {
        return GetFavoriteMoviesUseCaseImpl(repository)
    }

    @Provides
    @ViewModelScoped
    fun deleteMovieUseCase(repository: MoviesRepository): DeleteMovieUseCase {
        return DeleteMovieUseCaseImpl(repository)
    }
}
