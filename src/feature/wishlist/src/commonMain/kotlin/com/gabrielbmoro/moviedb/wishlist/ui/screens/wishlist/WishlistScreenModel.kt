package com.gabrielbmoro.moviedb.wishlist.ui.screens.wishlist

import com.gabrielbmoro.moviedb.domain.usecases.FavoriteMovieUseCase
import com.gabrielbmoro.moviedb.domain.usecases.GetFavoriteMoviesUseCase
import com.gabrielbmoro.moviedb.domain.usecases.IsFavoriteMovieUseCase
import com.gabrielbmoro.moviedb.platform.mvi.ScreenModelMVI
import org.koin.core.annotation.Factory

@Factory
class WishlistScreenModel(
    private val getFavoriteMoviesUseCase: GetFavoriteMoviesUseCase,
    private val favoriteMovieUseCase: FavoriteMovieUseCase,
    private val isFavoriteMovieUseCase: IsFavoriteMovieUseCase
) : ScreenModelMVI<WishlistUserIntent, WishlistUIState>() {
    override suspend fun execute(intent: WishlistUserIntent): WishlistUIState {
        return when (intent) {
            is WishlistUserIntent.DeleteMovie -> {
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
                    uiState.value.copy(
                        favoriteMovies = getFavoriteMoviesUseCase.execute(Unit),
                        isSuccessResult = true
                    )
                } else {
                    uiState.value
                }
            }

            is WishlistUserIntent.LoadMovies -> {
                val movies = getFavoriteMoviesUseCase.execute(Unit)
                uiState.value.copy(
                    favoriteMovies = movies
                )
            }

            is WishlistUserIntent.ResultMessageReset -> {
                uiState.value.copy(isSuccessResult = null)
            }
        }
    }

    override fun defaultEmptyState(): WishlistUIState = WishlistUIState()
}
