package com.gabrielbmoro.moviedb.wishlist.di

import com.gabrielbmoro.moviedb.wishlist.ui.screens.wishlist.WishlistScreenModel
import org.koin.dsl.module

val featureWishlistModule = module {
    factory {
        WishlistScreenModel(
            favoriteMovieUseCase = get(),
            getFavoriteMoviesUseCase = get(),
            isFavoriteMovieUseCase = get()
        )
    }
}
