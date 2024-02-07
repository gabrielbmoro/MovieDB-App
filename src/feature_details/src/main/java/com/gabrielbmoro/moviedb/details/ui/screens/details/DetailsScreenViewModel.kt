package com.gabrielbmoro.moviedb.details.ui.screens.details

import androidx.lifecycle.viewModelScope
import com.gabrielbmoro.moviedb.core.ui.mvi.ViewModelMVI
import com.gabrielbmoro.moviedb.details.domain.usecases.FavoriteMovieUseCase
import com.gabrielbmoro.moviedb.details.domain.usecases.GetMovieDetailsUseCase
import com.gabrielbmoro.moviedb.details.domain.usecases.IsFavoriteMovieUseCase
import com.gabrielbmoro.moviedb.repository.model.Movie
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailsScreenViewModel @Inject constructor(
    private val favoriteMovieUseCase: FavoriteMovieUseCase,
    private val isFavoriteMovieUseCase: IsFavoriteMovieUseCase,
    private val getMovieDetailsUseCase: GetMovieDetailsUseCase
) : ViewModelMVI<DetailsUserIntent, DetailsUIState>() {

    private lateinit var movie: Movie

    fun setup(movie: Movie) {
        this.movie = movie
        updateState(
            DetailsUIState(
                imageUrl = this.movie.backdropImageUrl,
                movieLanguage = this.movie.language,
                isFavorite = this.movie.isFavorite,
                movieOverview = this.movie.overview,
                moviePopularity = this.movie.popularity,
                movieTitle = this.movie.title,
                movieVotesAverage = this.movie.votesAverage
            )
        )

        checkIfMovieIsFavorite(movie.title)

        fetchMoviesDetails()
    }

    override fun defaultEmptyState() = DetailsUIState.empty()

    override suspend fun execute(intent: DetailsUserIntent): DetailsUIState {
        return when (intent) {
            is DetailsUserIntent.HideVideo -> {
                getState().copy(
                    showVideo = false
                )
            }

            is DetailsUserIntent.FavoriteMovie -> {
                val value = getState().isFavorite
                val desiredValue = value.not()
                val result = favoriteMovieUseCase.invoke(movie, toFavorite = desiredValue)
                if (result.data == true) {
                    getState().copy(
                        isFavorite = desiredValue
                    )
                } else {
                    getState()
                }
            }
        }
    }

    private fun checkIfMovieIsFavorite(movieTitle: String) {
        viewModelScope.launch {
            val data = isFavoriteMovieUseCase.invoke(movieTitle)
            if (data.data != null) {
                updateState(
                    getState().copy(
                        isFavorite = data.data ?: false
                    )
                )
            }
        }
    }

    private fun fetchMoviesDetails() {
        viewModelScope.launch {
            updateLoadingState(true)

            getMovieDetailsUseCase(movieId = movie.id)
                .catch {
                    updateState(
                        getState().copy(
                            isLoading = false,
                            errorMessage = "Something went wrong"
                        )
                    )
                }
                .collect { movieDetails ->
                    updateState(
                        getState().copy(
                            videoId = movieDetails.videoId,
                            tagLine = movieDetails.tagline,
                            status = movieDetails.status,
                            genres = movieDetails.genres,
                            homepage = movieDetails.homepage,
                            productionCompanies = movieDetails.productionCompanies.reduceToText()
                        )
                    )
                }
        }.invokeOnCompletion {
            updateLoadingState(false)
        }
    }

    private fun updateLoadingState(isLoading: Boolean) {
        updateState(getState().copy(isLoading = isLoading))
    }

    private fun List<String>.reduceToText() = reduce { acc, s -> "$acc, $s" }
}
