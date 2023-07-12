package com.gabrielbmoro.moviedb.ui.screens.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gabrielbmoro.moviedb.domain.model.Movie
import com.gabrielbmoro.moviedb.domain.usecases.FavoriteMovieUseCase
import com.gabrielbmoro.moviedb.domain.usecases.GetTrailersUseCase
import com.gabrielbmoro.moviedb.domain.usecases.IsFavoriteMovieUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class DetailsScreenViewModel(
    private val movie: Movie,
    private val favoriteMovieUseCase: FavoriteMovieUseCase,
    private val isFavoriteMovieUseCase: IsFavoriteMovieUseCase,
    private val getTrailersUseCase: GetTrailersUseCase,
) : ViewModel() {

    private val _uiState = MutableStateFlow(DetailsUIState.empty())
    val uiState = _uiState.stateIn(viewModelScope, SharingStarted.Eagerly, _uiState.value)

    init {
        _uiState.update {
            it.copy(
                imageUrl = movie.backdropImageUrl,
                movieLanguage = movie.language,
                isFavorite = movie.isFavorite,
                movieOverview = movie.overview,
                moviePopularity = movie.popularity,
                movieTitle = movie.title,
                movieVotesAverage = movie.votesAverage
            )
        }

        checkIfMovieIsFavorite(movie.title)

        fetchMoviesTrailer()
    }

    private fun checkIfMovieIsFavorite(movieTitle: String) {
        viewModelScope.launch {
            val data = isFavoriteMovieUseCase.invoke(movieTitle)
            if (data.data != null) {
                _uiState.update {
                    it.copy(
                        isFavorite = data.data
                    )
                }
            }
        }
    }

    private fun fetchMoviesTrailer() {
        viewModelScope.launch {
            _uiState.update {
                it.copy(
                    isLoading = true
                )
            }

            val data = getTrailersUseCase(movieId = movie.id)

            _uiState.update {
                it.copy(
                    videoId = data.data?.key
                )
            }
        }.invokeOnCompletion {
            _uiState.update {
                it.copy(
                    isLoading = false,
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