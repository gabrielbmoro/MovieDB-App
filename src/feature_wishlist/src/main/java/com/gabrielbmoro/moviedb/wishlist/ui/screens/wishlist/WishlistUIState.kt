package com.gabrielbmoro.moviedb.wishlist.ui.screens.wishlist

import com.gabrielbmoro.moviedb.repository.model.Movie

data class WishlistUIState(
    val favoriteMovies: List<Movie>? = null,
    val isLoading: Boolean = false,
    val areBarsVisible: Boolean = true
)
