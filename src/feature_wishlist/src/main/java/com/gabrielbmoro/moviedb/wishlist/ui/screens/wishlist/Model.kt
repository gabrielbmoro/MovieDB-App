package com.gabrielbmoro.moviedb.wishlist.ui.screens.wishlist

import com.gabrielbmoro.moviedb.repository.model.Movie

sealed class WishlistUserIntent {
    data class DeleteMovie(val movie: Movie) : WishlistUserIntent()
}

data class WishlistUIState(
    val favoriteMovies: List<Movie>? = null,
    val isLoading: Boolean = false,
    val areBarsVisible: Boolean = true
)
