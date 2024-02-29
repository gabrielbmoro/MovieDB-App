package com.gabrielbmoro.moviedb.wishlist.di

import com.gabrielbmoro.moviedb.wishlist.domain.usecases.DeleteMovieUseCase
import com.gabrielbmoro.moviedb.wishlist.domain.usecases.DeleteMovieUseCaseImpl
import com.gabrielbmoro.moviedb.wishlist.domain.usecases.GetFavoriteMoviesUseCase
import com.gabrielbmoro.moviedb.wishlist.domain.usecases.GetFavoriteMoviesUseCaseImpl
import com.gabrielbmoro.moviedb.wishlist.ui.screens.wishlist.WishlistViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.bind
import org.koin.dsl.module

val featureWishlistModule = module {
    viewModel {
        WishlistViewModel(
            deleteMovieUseCase = get(),
            getFavoriteMoviesUseCase = get(),
            resourcesProvider = get()
        )
    }

    factory {
        DeleteMovieUseCaseImpl(repository = get())
    }.bind(DeleteMovieUseCase::class)

    factory {
        GetFavoriteMoviesUseCaseImpl(repository = get())
    }.bind(GetFavoriteMoviesUseCase::class)
}
