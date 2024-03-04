package com.gabrielbmoro.moviedb.details.ui.screens.details

import androidx.lifecycle.viewModelScope
import com.gabrielbmoro.moviedb.core.ui.mvi.ViewModelMVI
import com.gabrielbmoro.moviedb.domain.usecases.FavoriteMovieUseCase
import com.gabrielbmoro.moviedb.domain.usecases.GetMovieDetailsUseCase
import com.gabrielbmoro.moviedb.domain.usecases.IsFavoriteMovieUseCase
import com.gabrielbmoro.moviedb.domain.entities.Movie
import kotlinx.coroutines.launch

class DetailsScreenViewModel(
    private val movie: Movie,
    private val favoriteMovieUseCase: FavoriteMovieUseCase,
    private val isFavoriteMovieUseCase: IsFavoriteMovieUseCase,
    private val getMovieDetailsUseCase: GetMovieDetailsUseCase
) : ViewModelMVI<DetailsUserIntent, DetailsUIState>() {

    init {
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
                favoriteMovieUseCase.invoke(movie, toFavorite = desiredValue)
                val result = isFavoriteMovieUseCase(movie.title)
                getState().copy(
                    isFavorite = result
                )
            }
        }
    }

    private fun checkIfMovieIsFavorite(movieTitle: String) {
        viewModelScope.launch {
            val data = isFavoriteMovieUseCase.invoke(movieTitle)
            updateState(
                getState().copy(
                    isFavorite = data
                )
            )
        }
    }

    private fun fetchMoviesDetails() {
        viewModelScope.launch {
            updateLoadingState(true)

            val movieDetails = getMovieDetailsUseCase(movieId = movie.id)
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

            updateLoadingState(false)
        }
    }

    private fun updateLoadingState(isLoading: Boolean) {
        updateState(getState().copy(isLoading = isLoading))
    }

    private fun List<String>.reduceToText() = reduce { acc, s -> "$acc, $s" }
}
