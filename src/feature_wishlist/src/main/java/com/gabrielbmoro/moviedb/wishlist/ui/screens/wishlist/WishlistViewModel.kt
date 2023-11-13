package com.gabrielbmoro.moviedb.wishlist.ui.screens.wishlist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gabrielbmoro.moviedb.wishlist.domain.usecases.GetFavoriteMoviesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class WishlistViewModel @Inject constructor(
    private val getFavoriteMoviesUseCase: GetFavoriteMoviesUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(
        WishlistUIState(
            favoriteMovies = null,
            isLoading = false
        )
    )
    val uiState = _uiState.stateIn(
        viewModelScope,
        SharingStarted.Eagerly,
        _uiState.value
    )

    fun load() = viewModelScope.async {
        getFavoriteMoviesUseCase().collect { movies ->
            _uiState.update {
                it.copy(
                    favoriteMovies = movies
                )
            }
        }
    }
}
