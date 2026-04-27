package com.gabrielbmoro.moviedb.wishlist.ui.screens.wishlist

import com.gabrielbmoro.moviedb.domain.MoviesRepository
import com.gabrielbmoro.moviedb.domain.entities.Movie
import com.gabrielbmoro.moviedb.domain.usecases.FavoriteMovieUseCase
import com.gabrielbmoro.moviedb.platform.viewmodel.BaseViewModel
import com.gabrielbmoro.moviedb.wishlist.ui.widgets.MovieCardInfo
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.CoroutineDispatcher
import org.koin.core.annotation.Provided

class WishlistViewModel(
    @Provided private val repository: MoviesRepository,
    private val favoriteMovieUseCase: FavoriteMovieUseCase,
    ioCoroutinesDispatcher: CoroutineDispatcher,
) : BaseViewModel<WishlistUIState, WishlistUserIntent, WishlistUiEvent>(ioCoroutinesDispatcher) {

    private var _movieToBeDeleted: MovieCardInfo? = null

    override fun executeIntent(intent: WishlistUserIntent) {
        when (intent) {
            is WishlistUserIntent.PrepareToDeleteMovie -> {
                launchIo {
                    handlePrepareToDeleteMovie(intent.movie)
                }
            }

            is WishlistUserIntent.LoadMovies -> {
                launchIo {
                    handleLoadMovies()
                }
            }

            is WishlistUserIntent.HideConfirmDeleteDialog -> launchIo {
                handleHideConfirmDeleteDialog()
            }

            WishlistUserIntent.DeleteMovie -> launchIo {
                handleDeleteMovie()
            }
        }
    }

    override fun defaultEmptyState(): WishlistUIState = WishlistUIState()

    override fun onFailure(throwable: Throwable) = Unit

    private suspend fun handleDeleteMovie() {
        _movieToBeDeleted?.let { movie ->
            favoriteMovieUseCase.execute(
                FavoriteMovieUseCase.Params(
                    movieTitle = movie.title,
                    toFavorite = false,
                ),
            )

            handleLoadMovies()

            fireEvent(WishlistUiEvent.ShowSuccessfulDeleteMessage)

            _movieToBeDeleted = null

            executeIntent(WishlistUserIntent.HideConfirmDeleteDialog)
        }
    }

    private suspend fun handleHideConfirmDeleteDialog() {
        updateState {
            it.copy(
                isDeleteAlertDialogVisible = false,
            )
        }
    }

    private suspend fun handleLoadMovies() {
        val movies = repository.getFavoriteMovies()
            .map(::toMovieCardInfo)
            .toImmutableList()
        updateState {
            it.copy(
                favoriteMovies = movies,
            )
        }
    }

    private suspend fun handlePrepareToDeleteMovie(movie: MovieCardInfo) {
        _movieToBeDeleted = movie
        updateState {
            it.copy(
                isDeleteAlertDialogVisible = true,
            )
        }
    }

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
