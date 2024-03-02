package com.gabrielbmoro.moviedb.wishlist.ui.screens.wishlist

import androidx.lifecycle.viewModelScope
import com.gabrielbmoro.moviedb.core.providers.resources.ResourcesProvider
import com.gabrielbmoro.moviedb.core.ui.mvi.ViewModelMVI
import com.gabrielbmoro.moviedb.feature.wishlist.R
import com.gabrielbmoro.moviedb.domain.usecases.FavoriteMovieUseCase
import com.gabrielbmoro.moviedb.domain.usecases.GetFavoriteMoviesUseCase
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class WishlistViewModel(
    private val getFavoriteMoviesUseCase: GetFavoriteMoviesUseCase,
    private val favoriteMovieUseCase: FavoriteMovieUseCase,
    private val resourcesProvider: ResourcesProvider
) : ViewModelMVI<WishlistUserIntent, WishlistUIState>() {
    fun load() = viewModelScope.launch {
        getFavoriteMoviesUseCase().collect { movies ->
            updateState(
                getState().copy(
                    favoriteMovies = movies
                )
            )
        }
    }

    override suspend fun execute(intent: WishlistUserIntent): WishlistUIState {
        return when (intent) {
            is WishlistUserIntent.DeleteMovie -> {
                val result = favoriteMovieUseCase(intent.movie, false)
                if (result.data == true) {
                    uiState.value.copy(
                        favoriteMovies = getFavoriteMoviesUseCase().first(),
                        resultMessage = resourcesProvider.getString(
                            R.string.delete_success_message
                        )
                    )
                } else {
                    uiState.value
                }
            }
        }
    }

    fun onResultMessageReset() {
        updateState(uiState.value.copy(resultMessage = null))
    }

    override fun defaultEmptyState(): WishlistUIState = WishlistUIState()
}
