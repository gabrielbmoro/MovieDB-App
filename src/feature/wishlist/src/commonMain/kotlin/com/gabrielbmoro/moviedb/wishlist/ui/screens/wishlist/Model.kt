package com.gabrielbmoro.moviedb.wishlist.ui.screens.wishlist

import com.gabrielbmoro.moviedb.domain.entities.Movie

sealed class WishlistUserIntent {
    data class PrepareToDeleteMovie(val movie: Movie) : WishlistUserIntent()

    data object DeleteMovie : WishlistUserIntent()

    data object LoadMovies : WishlistUserIntent()

    data object ResultMessageReset : WishlistUserIntent()

    data object HideConfirmDeleteDialog : WishlistUserIntent()
}

data class WishlistUIState(
    val favoriteMovies: List<Movie>? = null,
    val isLoading: Boolean = false,
    val areBarsVisible: Boolean = true,
    val isSuccessResult: Boolean? = null,
    val isDeleteAlertDialogVisible: Boolean = false,
)
