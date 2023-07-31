package com.gabrielbmoro.moviedb.ui.screens.wishlist

import com.gabrielbmoro.moviedb.domain.model.Movie

data class WishlistUIState(
    val favoriteMovies: List<Movie>? = null,
    val isLoading: Boolean = false,
    val areBarsVisible: Boolean = true,
)
