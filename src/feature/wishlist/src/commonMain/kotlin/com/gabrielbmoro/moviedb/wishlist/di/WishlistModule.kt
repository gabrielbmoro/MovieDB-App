package com.gabrielbmoro.moviedb.wishlist.di

import com.gabrielbmoro.moviedb.wishlist.ui.screens.wishlist.WishlistViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.lazyModule

val featureWishlistModule = lazyModule {
    viewModel {
        WishlistViewModel(
            getFavoriteMoviesUseCase = get(),
            isFavoriteMovieUseCase = get(),
            ioCoroutinesDispatcher = Dispatchers.IO,
            favoriteMovieUseCase = get()
        )
    }
}
