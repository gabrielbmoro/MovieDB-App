package com.gabrielbmoro.moviedb.wishlist.ui.screens.wishlist

import com.gabrielbmoro.moviedb.wishlist.ui.widgets.MovieCardInfo
import kotlinx.collections.immutable.ImmutableList

sealed interface WishlistUserIntent {
    data class PrepareToDeleteMovie(val movie: MovieCardInfo) : WishlistUserIntent

    data object DeleteMovie : WishlistUserIntent

    data object LoadMovies : WishlistUserIntent

    data object ResultMessageReset : WishlistUserIntent

    data object HideConfirmDeleteDialog : WishlistUserIntent
}

data class WishlistUIState(
    val favoriteMovies: ImmutableList<MovieCardInfo>? = null,
    val isLoading: Boolean = false,
    val areBarsVisible: Boolean = true,
    val isSuccessResult: Boolean? = null,
    val isDeleteAlertDialogVisible: Boolean = false,
)
