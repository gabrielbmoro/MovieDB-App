package com.gabrielbmoro.moviedb.wishlist.ui.screens.wishlist

import com.gabrielbmoro.moviedb.domain.entities.Movie

sealed class WishlistUserIntent {
    data class DeleteMovie(val movie: Movie) : WishlistUserIntent()

    data object LoadMovies : WishlistUserIntent()

    data object ResultMessageReset : WishlistUserIntent()
}

data class WishlistUIState(
    val favoriteMovies: List<Movie>? = null,
    val isLoading: Boolean = false,
    val areBarsVisible: Boolean = true,
    val isSuccessResult: Boolean? = null,
)
