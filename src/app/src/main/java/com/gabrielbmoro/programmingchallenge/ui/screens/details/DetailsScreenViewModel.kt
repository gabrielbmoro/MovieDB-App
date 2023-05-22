package com.gabrielbmoro.programmingchallenge.ui.screens.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gabrielbmoro.programmingchallenge.core.di.ConfigVariables
import com.gabrielbmoro.programmingchallenge.domain.model.Movie
import com.gabrielbmoro.programmingchallenge.domain.usecases.FavoriteMovieUseCase
import com.gabrielbmoro.programmingchallenge.domain.usecases.IsFavoriteMovieUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class DetailsScreenViewModel constructor(
    private val movie: Movie,
    private val favoriteMovieUseCase: FavoriteMovieUseCase,
    private val isFavoriteMovieUseCase: IsFavoriteMovieUseCase,
) : ViewModel() {

    private val _uiState = MutableStateFlow(DetailsUIState.empty())
    val uiState = _uiState.stateIn(viewModelScope, SharingStarted.Eagerly, _uiState.value)

    init {
        _uiState.update {
            it.copy(
                imageUrl = movie.backdropImageUrl.let {
                    "${ConfigVariables.BIG_SIZE_IMAGE_ADDRESS}${movie.backdropImageUrl}"
                },
                movieLanguage = movie.language,
                isFavorite = movie.isFavorite,
                movieOverview = movie.overview,
                moviePopularity = movie.popularity,
                movieTitle = movie.title,
                movieVotesAverage = movie.votesAverage
            )
        }

        viewModelScope.launch {
            checkIfMovieIsFavorite(movie.title)
        }
    }

    private suspend fun checkIfMovieIsFavorite(movieTitle: String) {
        val data = isFavoriteMovieUseCase.invoke(movieTitle)
        if (data.data != null) {
            _uiState.update {
                it.copy(
                    isFavorite = data.data
                )
            }
        }
    }

    fun isToFavoriteOrUnFavorite(isToFavorite: Boolean) {
        viewModelScope.launch {
            val response = favoriteMovieUseCase(movie, isToFavorite)

            if (response.data != null) {
                movie.isFavorite = isToFavorite
                _uiState.update { it.copy(isFavorite = movie.isFavorite) }
            }
        }
    }
}