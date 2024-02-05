package com.gabrielbmoro.moviedb.wishlist.ui.screens.wishlist

import androidx.lifecycle.viewModelScope
import com.gabrielbmoro.moviedb.core.ui.mvi.ViewModelMVI
import com.gabrielbmoro.moviedb.wishlist.domain.usecases.GetFavoriteMoviesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import javax.inject.Inject

@HiltViewModel
class WishlistViewModel @Inject constructor(
    private val getFavoriteMoviesUseCase: GetFavoriteMoviesUseCase
) : ViewModelMVI<Any, WishlistUIState>() {

    fun load() = viewModelScope.async {
        getFavoriteMoviesUseCase().collect { movies ->
            updateState(
                getState().copy(
                    favoriteMovies = movies
                )
            )
        }
    }

    override suspend fun execute(intent: Any): WishlistUIState {
        TODO("Not yet implemented")
    }

    override fun defaultEmptyState(): WishlistUIState = WishlistUIState()
}
