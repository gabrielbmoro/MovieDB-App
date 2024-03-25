package com.gabrielbmoro.moviedb.wishlist.di

import cafe.adriel.voyager.core.screen.Screen
import com.gabrielbmoro.moviedb.platform.navigation.NavigationDestinations
import com.gabrielbmoro.moviedb.wishlist.ui.screens.wishlist.WishlistScreen
import com.gabrielbmoro.moviedb.wishlist.ui.screens.wishlist.WishlistScreenModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

val featureWishlistModule = module {
    factory {
        WishlistScreenModel(
            favoriteMovieUseCase = get(),
            getFavoriteMoviesUseCase = get(),
            isFavoriteMovieUseCase = get()
        )
    }

    factory<Screen>(named(NavigationDestinations.WISHLIST)) {
        WishlistScreen()
    }
}
