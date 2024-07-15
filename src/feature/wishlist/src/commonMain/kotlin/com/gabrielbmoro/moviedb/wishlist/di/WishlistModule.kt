package com.gabrielbmoro.moviedb.wishlist.di

import cafe.adriel.voyager.core.screen.Screen
import com.gabrielbmoro.moviedb.platform.navigation.NavigationDestinations
import com.gabrielbmoro.moviedb.wishlist.ui.screens.wishlist.WishlistScreen
import org.koin.core.annotation.ComponentScan
import org.koin.core.annotation.Factory
import org.koin.core.annotation.Module
import org.koin.core.annotation.Named
import org.koin.ksp.generated.module

val featureWishlistModule = WishlistModule().module

@Module
@ComponentScan("com.gabrielbmoro.moviedb.wishlist.ui")
class WishlistModule {

    @Factory
    @Named(NavigationDestinations.WISHLIST)
    fun wishlistScreen(): Screen {
        return WishlistScreen()
    }
}