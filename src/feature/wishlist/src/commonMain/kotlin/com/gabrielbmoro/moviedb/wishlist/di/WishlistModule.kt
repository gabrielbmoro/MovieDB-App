package com.gabrielbmoro.moviedb.wishlist.di

import com.gabrielbmoro.moviedb.wishlist.ui.screens.wishlist.WishlistViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import org.koin.compose.viewmodel.dsl.viewModel
import org.koin.dsl.module

val featureWishlistModule = module {
    viewModel {
        WishlistViewModel(
            getFavoriteMoviesUseCase = get(),
            isFavoriteMovieUseCase = get(),
            ioCoroutinesDispatcher = Dispatchers.IO,
            favoriteMovieUseCase = get()
        )
    }
}
