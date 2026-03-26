package com.gabrielbmoro.moviedb.wishlist.di

import com.gabrielbmoro.moviedb.domain.di.DomainModule
import com.gabrielbmoro.moviedb.wishlist.ui.screens.wishlist.WishlistViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.lazyModule
import org.koin.ksp.generated.module

val featureWishlistModule = lazyModule {
    includes(DomainModule().module)
    viewModel {
        WishlistViewModel(
            repository = get(),
            favoriteMovieUseCase = get(),
            ioCoroutinesDispatcher = Dispatchers.IO,
        )
    }
}
