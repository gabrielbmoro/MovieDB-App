package com.gabrielbmoro.moviedb.wishlist.di

import com.gabrielbmoro.moviedb.wishlist.ui.screens.wishlist.WishlistViewModel
import org.koin.dsl.module

val featureWishlistModule = module {
    factory {
        WishlistViewModel(
            favoriteMovieUseCase = get(),
            getFavoriteMoviesUseCase = get(),
            resourcesProvider = get(),
            isFavoriteMovieUseCase = get()
        )
    }
}
