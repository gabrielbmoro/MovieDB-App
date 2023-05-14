package com.gabrielbmoro.programmingchallenge.ui.screens.details

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gabrielbmoro.programmingchallenge.core.di.ConfigVariables
import com.gabrielbmoro.programmingchallenge.domain.model.Movie
import com.gabrielbmoro.programmingchallenge.domain.usecases.FavoriteMovieUseCase
import com.gabrielbmoro.programmingchallenge.domain.usecases.IsFavoriteMovieUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailsScreenViewModel @Inject constructor(
    private val favoriteMovieUseCase: FavoriteMovieUseCase,
    private val isFavoriteMovieUseCase: IsFavoriteMovieUseCase,
) : ViewModel() {

    private val _uiState = mutableStateOf(DetailsUIState.empty())
    val uiState: State<DetailsUIState> = _uiState

    suspend fun setup(movie: Movie) {
        _uiState.value = _uiState.value.copy(
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

        checkIfMovieIsFavorite(movie.title)
    }

    private suspend fun checkIfMovieIsFavorite(movieTitle: String) {
        val data = isFavoriteMovieUseCase.invoke(movieTitle)
        if (data.data != null) {
            _uiState.value = _uiState.value.copy(
                isFavorite = data.data
            )
        }
    }

    fun isToFavoriteOrUnFavorite(isToFavorite: Boolean, movie: Movie) {
        viewModelScope.launch {
            val response = favoriteMovieUseCase(movie, isToFavorite)

            if (response.data != null) {
                movie.isFavorite = isToFavorite
                _uiState.value = _uiState.value.copy(isFavorite = movie.isFavorite)
            }
        }
    }
}