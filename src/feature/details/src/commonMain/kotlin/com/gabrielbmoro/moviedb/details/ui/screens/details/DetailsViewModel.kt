package com.gabrielbmoro.moviedb.details.ui.screens.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gabrielbmoro.moviedb.domain.entities.MovieDetail
import com.gabrielbmoro.moviedb.domain.usecases.FavoriteMovieUseCase
import com.gabrielbmoro.moviedb.domain.usecases.GetMovieDetailsUseCase
import com.gabrielbmoro.moviedb.domain.usecases.IsFavoriteMovieUseCase
import com.gabrielbmoro.moviedb.platform.ViewModelMvi
import kotlinx.collections.immutable.toImmutableList
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
) : ViewModel(), ViewModelMvi<DetailsUserIntent> {

    private val _uiState = MutableStateFlow(this.defaultEmptyState())
    val uiState = _uiState.stateIn(viewModelScope, SharingStarted.Eagerly, _uiState.value)

    private lateinit var movieDetails: MovieDetail
    private var movieId: Long? = null

    private fun defaultEmptyState() = DetailsUIState.empty()

    override fun execute(intent: DetailsUserIntent) {
        when (intent) {
            is DetailsUserIntent.HideVideo -> hideVideo()

            is DetailsUserIntent.FavoriteMovie -> favoriteMovie()

            is DetailsUserIntent.LoadMovieDetails -> loadMovieDetails(intent)
        }
    }

    private fun hideVideo() {
        _uiState.update {
            it.copy(
                showVideo = false,
            )
        }
    }

    private fun favoriteMovie() {
        val value = uiState.value.isFavorite
        val desiredValue = value.not()

        val params = movieDetails.toFavoriteUseCaseParam(
            movieId = movieId!!,
            desiredValue = desiredValue,
        )
        viewModelScope.launch(ioDispatcher) {
            favoriteMovieUseCase.execute(params)

            val result =
                isFavoriteMovieUseCase.execute(
                    IsFavoriteMovieUseCase.Params(
                        movieTitle = movieDetails.title,
                    ),
                )
            _uiState.update {
                it.copy(
                    isFavorite = result,
                )
            }
        }
    }

    private fun loadMovieDetails(intent: DetailsUserIntent.LoadMovieDetails) {
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
                    genres = movieDetails.genres.toImmutableList(),
                    homepage = movieDetails.homepage,
                    productionCompanies = movieDetails
                        .productionCompanies.takeIf { productionCompanies ->
                            productionCompanies.isNotEmpty()
                        }?.reduceToText(),
                    movieTitle = movieDetails.title,
                    movieOverview = movieDetails.overview,
                    movieLanguage = movieDetails.language,
                    moviePopularity = movieDetails.popularity,
                    movieVotesAverage = movieDetails.votesAverage,
                    imageUrl = movieDetails.backdropImageUrl,
                )
            }
        }
    }

    private suspend fun isMovieFavorite(movieTitle: String): Boolean {
        return isFavoriteMovieUseCase.execute(
            IsFavoriteMovieUseCase.Params(
                movieTitle = movieTitle,
            ),
        )
    }

    private suspend fun fetchMoviesDetails(): MovieDetail {
        return getMovieDetailsUseCase.execute(
            GetMovieDetailsUseCase.Params(
                movieId = movieId!!,
            ),
        )
    }

    private fun List<String>.reduceToText() = reduce { acc, s ->
        "$acc, $s"
    }
}

private fun MovieDetail.toFavoriteUseCaseParam(
    movieId: Long,
    desiredValue: Boolean,
): FavoriteMovieUseCase.Params {
    return FavoriteMovieUseCase.Params(
        movieTitle = title,
        movieLanguage = language,
        movieVotesAverage = votesAverage,
        movieReleaseDate = releaseDate,
        moviePosterImageUrl = posterImageUrl,
        moviePopularity = popularity,
        movieOverview = overview,
        movieId = movieId,
        movieBackdropImageUrl = backdropImageUrl,
        toFavorite = desiredValue,
    )
}
