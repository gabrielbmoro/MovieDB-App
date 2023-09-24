package com.gabrielbmoro.moviedb.details.ui.screens.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gabrielbmoro.moviedb.details.domain.usecases.FavoriteMovieUseCase
import com.gabrielbmoro.moviedb.details.domain.usecases.GetMovieDetailsUseCase
import com.gabrielbmoro.moviedb.details.domain.usecases.IsFavoriteMovieUseCase
import com.gabrielbmoro.moviedb.repository.model.Movie
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailsScreenViewModel @Inject constructor(
    private val favoriteMovieUseCase: FavoriteMovieUseCase,
    private val isFavoriteMovieUseCase: IsFavoriteMovieUseCase,
    private val getMovieDetailsUseCase: GetMovieDetailsUseCase
) : ViewModel() {

    private lateinit var movie: Movie
    private val _uiState = MutableStateFlow(DetailsUIState.empty())
    val uiState = _uiState.stateIn(viewModelScope, SharingStarted.Eagerly, _uiState.value)

    fun setup(movie: Movie) {
        this.movie = movie

        _uiState.update {
            it.copy(
                imageUrl = this.movie.backdropImageUrl,
                movieLanguage = this.movie.language,
                isFavorite = this.movie.isFavorite,
                movieOverview = this.movie.overview,
                moviePopularity = this.movie.popularity,
                movieTitle = this.movie.title,
                movieVotesAverage = this.movie.votesAverage
            )
        }

        checkIfMovieIsFavorite(movie.title)

        fetchMoviesDetails()
    }

    private fun checkIfMovieIsFavorite(movieTitle: String) {
        viewModelScope.launch {
            val data = isFavoriteMovieUseCase.invoke(movieTitle)
            if (data.data != null) {
                _uiState.update {
                    it.copy(
                        isFavorite = data.data!!
                    )
                }
            }
        }
    }

    private fun fetchMoviesDetails() {
        viewModelScope.launch {
            _uiState.update {
                it.copy(
                    isLoading = true
                )
            }

            getMovieDetailsUseCase(movieId = movie.id).collect { movieDetails ->
                _uiState.update {
                    it.copy(
                        videoId = movieDetails.videoId,
                        tagLine = movieDetails.tagline,
                        status = movieDetails.status,
                        genres = movieDetails.genres.reduceToText(),
                        homepage = movieDetails.homepage,
                        productionCompanies = movieDetails.productionCompanies.reduceToText()
                    )
                }
            }
        }.invokeOnCompletion {
            _uiState.update {
                it.copy(
                    isLoading = false
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


    private fun List<String>.reduceToText() = reduce { acc, s -> "$acc, $s" }

}
