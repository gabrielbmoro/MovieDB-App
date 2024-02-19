package com.gabrielbmoro.moviedb.wishlist.ui.screens.wishlist

import androidx.lifecycle.viewModelScope
import com.gabrielbmoro.moviedb.core.providers.resources.ResourcesProvider
import com.gabrielbmoro.moviedb.core.ui.mvi.ViewModelMVI
import com.gabrielbmoro.moviedb.feature.wishlist.R
import com.gabrielbmoro.moviedb.wishlist.domain.usecases.DeleteMovieUseCase
import com.gabrielbmoro.moviedb.wishlist.domain.usecases.GetFavoriteMoviesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WishlistViewModel @Inject constructor(
    private val getFavoriteMoviesUseCase: GetFavoriteMoviesUseCase,
    private val deleteMovieUseCase: DeleteMovieUseCase,
    private val resourcesProvider: ResourcesProvider
) : ViewModelMVI<WishlistUserIntent, WishlistUIState, String>() {
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
                val result = deleteMovieUseCase(intent.movie.title)
                if (result == true) {
                    publish(
                        resourcesProvider.getString(R.string.delete_success_message)
                    )

                    uiState.value.copy(favoriteMovies = getFavoriteMoviesUseCase().first())
                } else {
                    uiState.value
                }
            }
        }
    }

    override fun defaultEmptyState(): WishlistUIState = WishlistUIState()
}
