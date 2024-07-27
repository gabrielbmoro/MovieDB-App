package com.gabrielbmoro.moviedb.details.ui.screens.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gabrielbmoro.moviedb.domain.entities.MovieDetail
import com.gabrielbmoro.moviedb.domain.usecases.FavoriteMovieUseCase
import com.gabrielbmoro.moviedb.domain.usecases.GetMovieDetailsUseCase
import com.gabrielbmoro.moviedb.domain.usecases.IsFavoriteMovieUseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class DetailsViewModel(
    private val favoriteMovieUseCase: FavoriteMovieUseCase,
    private val isFavoriteMovieUseCase: IsFavoriteMovieUseCase,
    private val getMovieDetailsUseCase: GetMovieDetailsUseCase,
    private val ioDispatcher: CoroutineDispatcher,
) : ViewModel() {

    private val _uiState = MutableStateFlow(this.defaultEmptyState())
    val uiState = _uiState.stateIn(viewModelScope, SharingStarted.Eagerly, _uiState.value)

    private lateinit var movieDetails: MovieDetail
    private var movieId: Long? = null

    private fun defaultEmptyState() = DetailsUIState.empty()

    fun execute(intent: DetailsUserIntent) {
        when (intent) {
            is DetailsUserIntent.HideVideo -> {
                _uiState.update {
                    it.copy(
                        showVideo = false
                    )
                }
            }

            is DetailsUserIntent.FavoriteMovie -> {
                val value = uiState.value.isFavorite
                val desiredValue = value.not()

                val params =
                    FavoriteMovieUseCase.Params(
                        movieTitle = movieDetails.title,
                        movieLanguage = movieDetails.language,
                        movieVotesAverage = movieDetails.votesAverage,
                        movieReleaseDate = movieDetails.releaseDate,
                        moviePosterImageUrl = movieDetails.posterImageUrl,
                        moviePopularity = movieDetails.popularity,
                        movieOverview = movieDetails.overview,
                        movieId = movieId,
                        movieBackdropImageUrl = movieDetails.backdropImageUrl,
                        toFavorite = desiredValue
                    )
                viewModelScope.launch(ioDispatcher) {
                    favoriteMovieUseCase.execute(params)

                    val result =
                        isFavoriteMovieUseCase.execute(
                            IsFavoriteMovieUseCase.Params(
                                movieTitle = movieDetails.title
                            )
                        )
                    _uiState.update {
                        it.copy(
                            isFavorite = result
                        )
                    }
                }
            }

            is DetailsUserIntent.LoadMovieDetails -> {
                this.movieId = intent.movieId

                viewModelScope.launch(ioDispatcher) {
                    _uiState.update {
                        it.copy(isLoading = true)
                    }

                    movieDetails = viewModelScope.async(ioDispatcher) {
                        fetchMoviesDetails()
                    }.await()

                    val isMovieFavorite = viewModelScope.async(ioDispatcher) {
                        isMovieFavorite(movieTitle = movieDetails.title)
                    }.await()

                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            isFavorite = isMovieFavorite,
                            videoId = movieDetails.videoId,
                            tagLine = movieDetails.tagline,
                            status = movieDetails.status,
                            genres = movieDetails.genres,
                            homepage = movieDetails.homepage,
                            productionCompanies = movieDetails.productionCompanies.reduceToText(),
                            movieTitle = movieDetails.title,
                            movieOverview = movieDetails.overview,
                            movieLanguage = movieDetails.language,
                            moviePopularity = movieDetails.popularity,
                            movieVotesAverage = movieDetails.votesAverage,
                            imageUrl = movieDetails.backdropImageUrl
                        )
                    }
                }
            }
        }
    }

    private suspend fun isMovieFavorite(movieTitle: String): Boolean {
        return isFavoriteMovieUseCase.execute(
            IsFavoriteMovieUseCase.Params(
                movieTitle = movieTitle
            )
        )
    }

    private suspend fun fetchMoviesDetails(): MovieDetail {
        return getMovieDetailsUseCase.execute(
            GetMovieDetailsUseCase.Params(
                movieId = movieId!!
            )
        )
    }

    private fun List<String>.reduceToText() = reduce { acc, s ->
        "$acc, $s"
    }
}
