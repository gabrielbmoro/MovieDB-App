package com.gabrielbmoro.moviedb.wishlist.ui.screens.wishlist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gabrielbmoro.moviedb.domain.entities.Movie
import com.gabrielbmoro.moviedb.domain.usecases.FavoriteMovieUseCase
import com.gabrielbmoro.moviedb.domain.usecases.GetFavoriteMoviesUseCase
import com.gabrielbmoro.moviedb.domain.usecases.IsFavoriteMovieUseCase
import com.gabrielbmoro.moviedb.platform.ViewModelMvi
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class WishlistViewModel(
    private val getFavoriteMoviesUseCase: GetFavoriteMoviesUseCase,
    private val favoriteMovieUseCase: FavoriteMovieUseCase,
    private val isFavoriteMovieUseCase: IsFavoriteMovieUseCase,
    private val ioCoroutinesDispatcher: CoroutineDispatcher
) : ViewModel(), ViewModelMvi<WishlistUserIntent> {

    private val _uiState = MutableStateFlow(this.defaultEmptyState())
    val uiState = _uiState.stateIn(viewModelScope, SharingStarted.Eagerly, _uiState.value)

    private var _movieToBeDeleted: Movie? = null

    override fun execute(intent: WishlistUserIntent) {
        when (intent) {
            is WishlistUserIntent.PrepareToDeleteMovie -> {
                _movieToBeDeleted = intent.movie
                _uiState.update {
                    it.copy(
                        isDeleteAlertDialogVisible = true
                    )
                }
            }

            is WishlistUserIntent.LoadMovies -> {
                viewModelScope.launch(ioCoroutinesDispatcher) {
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

            is WishlistUserIntent.HideConfirmDeleteDialog -> {
                _uiState.update {
                    it.copy(
                        isDeleteAlertDialogVisible = false
                    )
                }
            }

            WishlistUserIntent.DeleteMovie -> viewModelScope.launch(ioCoroutinesDispatcher) {
                deleteMovie()

                execute(WishlistUserIntent.HideConfirmDeleteDialog)
            }
        }
    }

    private fun defaultEmptyState(): WishlistUIState = WishlistUIState()

    private suspend fun deleteMovie() {
        _movieToBeDeleted?.let { movie ->
            favoriteMovieUseCase.execute(
                FavoriteMovieUseCase.Params(
                    movieTitle = movie.title,
                    toFavorite = false
                )
            )
            val result =
                isFavoriteMovieUseCase.execute(
                    IsFavoriteMovieUseCase.Params(
                        movieTitle = movie.title
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

            _movieToBeDeleted = null
        }
    }
}
