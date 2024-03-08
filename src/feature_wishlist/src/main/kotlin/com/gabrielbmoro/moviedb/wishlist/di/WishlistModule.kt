package com.gabrielbmoro.moviedb.wishlist.di

import com.gabrielbmoro.moviedb.wishlist.ui.screens.wishlist.WishlistViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val featureWishlistModule = module {
    viewModel {
        WishlistViewModel(
            favoriteMovieUseCase = get(),
            getFavoriteMoviesUseCase = get(),
            resourcesProvider = get(),
            isFavoriteMovieUseCase = get()
        )
    }
}
