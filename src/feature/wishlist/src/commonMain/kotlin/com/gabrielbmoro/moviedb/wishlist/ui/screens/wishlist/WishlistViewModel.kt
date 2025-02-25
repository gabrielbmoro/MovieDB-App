package com.gabrielbmoro.moviedb.wishlist.ui.screens.wishlist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gabrielbmoro.moviedb.domain.entities.Movie
import com.gabrielbmoro.moviedb.domain.usecases.FavoriteMovieUseCase
import com.gabrielbmoro.moviedb.domain.usecases.GetFavoriteMoviesUseCase
import com.gabrielbmoro.moviedb.domain.usecases.IsFavoriteMovieUseCase
import com.gabrielbmoro.moviedb.platform.IntentExecutor
import com.gabrielbmoro.moviedb.wishlist.ui.widgets.MovieCardInfo
import kotlinx.collections.immutable.toImmutableList
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
    private val ioCoroutinesDispatcher: CoroutineDispatcher,
) : ViewModel(), IntentExecutor<WishlistUserIntent> {

    private val _uiState = MutableStateFlow(this.defaultEmptyState())
    val uiState = _uiState.stateIn(viewModelScope, SharingStarted.Eagerly, _uiState.value)

    private var _movieToBeDeleted: MovieCardInfo? = null

    override fun execute(intent: WishlistUserIntent) {
        when (intent) {
            is WishlistUserIntent.PrepareToDeleteMovie -> handlePrepareToDeleteMovie(intent.movie)

            is WishlistUserIntent.LoadMovies -> handleLoadMovies()

            is WishlistUserIntent.ResultMessageReset -> handleResultMessageReset()

            is WishlistUserIntent.HideConfirmDeleteDialog -> handleHideConfirmDeleteDialog()

            WishlistUserIntent.DeleteMovie -> handleDeleteMovie()
        }
    }

    private fun handleDeleteMovie() {
        viewModelScope.launch(ioCoroutinesDispatcher) {
            _movieToBeDeleted?.let { movie ->
                favoriteMovieUseCase.execute(
                    FavoriteMovieUseCase.Params(
                        movieTitle = movie.title,
                        toFavorite = false,
                    ),
                )
                val isFavoriteAfterDeletion =
                    isFavoriteMovieUseCase.execute(
                        IsFavoriteMovieUseCase.Params(
                            movieTitle = movie.title,
                        ),
                    )
                if (!isFavoriteAfterDeletion) {
                    val favoriteMovies = getFavoriteMoviesUseCase.execute(Unit)
                        .map(::toMovieCardInfo)
                        .toImmutableList()
                    _uiState.update {
                        it.copy(
                            favoriteMovies = favoriteMovies,
                            isSuccessResult = true,
                        )
                    }
                }

                _movieToBeDeleted = null
            }

            execute(WishlistUserIntent.HideConfirmDeleteDialog)
        }
    }

    private fun handleHideConfirmDeleteDialog() {
        _uiState.update {
            it.copy(
                isDeleteAlertDialogVisible = false,
            )
        }
    }

    private fun handleResultMessageReset() {
        _uiState.update { it.copy(isSuccessResult = null) }
    }

    private fun handleLoadMovies() {
        viewModelScope.launch(ioCoroutinesDispatcher) {
            val movies = getFavoriteMoviesUseCase.execute(Unit)
                .map(::toMovieCardInfo)
                .toImmutableList()
            _uiState.update {
                it.copy(
                    favoriteMovies = movies,
                )
            }
        }
    }

    private fun handlePrepareToDeleteMovie(movie: MovieCardInfo) {
        _movieToBeDeleted = movie
        _uiState.update {
            it.copy(
                isDeleteAlertDialogVisible = true,
            )
        }
    }

    private fun defaultEmptyState(): WishlistUIState = WishlistUIState()

    private fun toMovieCardInfo(movie: Movie): MovieCardInfo {
        return MovieCardInfo(
            id = movie.id,
            title = movie.title,
            overview = movie.overview,
            votesAverage = movie.votesAverage,
            posterImageUrl = movie.posterImageUrl,
        )
    }

    override fun onCleared() {
        _movieToBeDeleted = null

        super.onCleared()
    }
}
