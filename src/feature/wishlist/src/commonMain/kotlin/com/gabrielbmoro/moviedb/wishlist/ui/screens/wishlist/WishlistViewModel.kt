package com.gabrielbmoro.moviedb.wishlist.ui.screens.wishlist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gabrielbmoro.moviedb.domain.usecases.FavoriteMovieUseCase
import com.gabrielbmoro.moviedb.domain.usecases.GetFavoriteMoviesUseCase
import com.gabrielbmoro.moviedb.domain.usecases.IsFavoriteMovieUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.koin.android.annotation.KoinViewModel
import org.koin.core.annotation.Factory

@Factory
class WishlistViewModel(
    private val getFavoriteMoviesUseCase: GetFavoriteMoviesUseCase,
    private val favoriteMovieUseCase: FavoriteMovieUseCase,
    private val isFavoriteMovieUseCase: IsFavoriteMovieUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(this.defaultEmptyState())
    val uiState = _uiState.stateIn(viewModelScope, SharingStarted.Eagerly, _uiState.value)

    fun execute(intent: WishlistUserIntent) {
        when (intent) {
            is WishlistUserIntent.DeleteMovie -> {
                viewModelScope.launch {
                    favoriteMovieUseCase.execute(
                        FavoriteMovieUseCase.Params(
                            movieTitle = intent.movie.title,
                            toFavorite = false
                        )
                    )
                    val result =
                        isFavoriteMovieUseCase.execute(
                            IsFavoriteMovieUseCase.Params(
                                movieTitle = intent.movie.title
                            )
                        )
                    if (!result) {
                        _uiState.update {
                            it.copy(
                                favoriteMovies = getFavoriteMoviesUseCase.execute(Unit),
                                isSuccessResult = true
                            )
                        }
                    }
                }
            }

            is WishlistUserIntent.LoadMovies -> {
                viewModelScope.launch {
                    val movies = getFavoriteMoviesUseCase.execute(Unit)
                    _uiState.update {
                        it.copy(
                            favoriteMovies = movies
                        )
                    }
                }
            }

            is WishlistUserIntent.ResultMessageReset -> {
                _uiState.update { it.copy(isSuccessResult = null) }
            }
        }
    }

    private fun defaultEmptyState(): WishlistUIState = WishlistUIState()
}
