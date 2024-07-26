package com.gabrielbmoro.moviedb.wishlist.di

import org.koin.core.annotation.ComponentScan
import org.koin.core.annotation.Module
import org.koin.ksp.generated.module

val featureWishlistModule = WishlistModule().module

@Module
@ComponentScan("com.gabrielbmoro.moviedb.wishlist.ui")
class WishlistModule