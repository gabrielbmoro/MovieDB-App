package com.gabrielbmoro.moviedb.wishlist.ui.screens.wishlist

import com.gabrielbmoro.moviedb.core.providers.resources.ResourcesProvider
import com.gabrielbmoro.moviedb.core.ui.mvi.ViewModelMVI
import com.gabrielbmoro.moviedb.feature.wishlist.R
import com.gabrielbmoro.moviedb.domain.usecases.FavoriteMovieUseCase
import com.gabrielbmoro.moviedb.domain.usecases.GetFavoriteMoviesUseCase
import com.gabrielbmoro.moviedb.domain.usecases.IsFavoriteMovieUseCase

class WishlistViewModel(
    private val getFavoriteMoviesUseCase: GetFavoriteMoviesUseCase,
    private val favoriteMovieUseCase: FavoriteMovieUseCase,
    private val isFavoriteMovieUseCase: IsFavoriteMovieUseCase,
    private val resourcesProvider: ResourcesProvider
) : ViewModelMVI<WishlistUserIntent, WishlistUIState>() {

    override suspend fun setup(): WishlistUIState {
        val movies = getFavoriteMoviesUseCase.execute(Unit)
        return uiState.value.copy(
            favoriteMovies = movies
        )
    }

    override suspend fun execute(intent: WishlistUserIntent): WishlistUIState {
        return when (intent) {
            is WishlistUserIntent.DeleteMovie -> {
                favoriteMovieUseCase.execute(
                    FavoriteMovieUseCase.Params(
                        movieTitle = intent.movie.title,
                        toFavorite = false
                    )
                )
                val result = isFavoriteMovieUseCase.execute(
                    IsFavoriteMovieUseCase.Params(
                        movieTitle = intent.movie.title
                    )
                )
                if (!result) {
                    uiState.value.copy(
                        favoriteMovies = getFavoriteMoviesUseCase.execute(Unit),
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
