package com.gabrielbmoro.moviedb.wishlist.di

import com.gabrielbmoro.moviedb.wishlist.domain.usecases.GetFavoriteMoviesUseCase
import com.gabrielbmoro.moviedb.wishlist.ui.screens.wishlist.WishlistViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

object WishlistModule {
    val module = module {
        viewModel {
            WishlistViewModel(
                get(),
            )
        }

        single {
            GetFavoriteMoviesUseCase(get())
        }
    }
}