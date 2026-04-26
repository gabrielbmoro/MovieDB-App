package com.gabrielbmoro.moviedb.details.ui.screens.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gabrielbmoro.moviedb.domain.MoviesRepository
import com.gabrielbmoro.moviedb.domain.entities.MovieDetail
import com.gabrielbmoro.moviedb.domain.usecases.FavoriteMovieUseCase
import com.gabrielbmoro.moviedb.domain.usecases.GetMovieDetailsUseCase
import com.gabrielbmoro.moviedb.platform.ViewModelMvi
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.koin.core.annotation.Provided

class DetailsViewModel(
    @Provided private val repository: MoviesRepository,
    private val favoriteMovieUseCase: FavoriteMovieUseCase,
    private val getMovieDetailsUseCase: GetMovieDetailsUseCase,
    private val ioDispatcher: CoroutineDispatcher,
) : ViewModel(), ViewModelMvi<DetailsUserIntent> {

    private val _uiState = MutableStateFlow(this.defaultEmptyState())
    val uiState = _uiState.stateIn(viewModelScope, SharingStarted.Eagerly, _uiState.value)

    private var movieDetails: MovieDetail? = null
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

        movieDetails?.toFavoriteUseCaseParam(
            movieId = movieId!!,
            desiredValue = desiredValue,
        )?.let { params ->
            viewModelScope.launch(ioDispatcher) {
                favoriteMovieUseCase.execute(params)

                repository.checkIsAFavoriteMovie(
                    movieTitle = movieDetails!!.title,
                ).let { result ->
                    _uiState.update {
                        it.copy(
                            isFavorite = result,
                        )
                    }
                }
            }
        }
    }

    private fun loadMovieDetails(intent: DetailsUserIntent.LoadMovieDetails) {
        this.movieId = intent.movieId

        viewModelScope.launch(ioDispatcher) {
            _uiState.update {
                it.copy(isLoading = true)
            }

            viewModelScope.async(ioDispatcher) {
                fetchMoviesDetails()
            }.await()?.let { details ->
                val isMovieFavorite = viewModelScope.async(ioDispatcher) {
                    isMovieFavorite(movieTitle = details.title)
                }.await()

                _uiState.update {
                    it.copy(
                        isLoading = false,
                        isFavorite = isMovieFavorite,
                        videoId = details.videoId,
                        tagLine = details.tagline,
                        status = details.status,
                        genres = details.genres.toImmutableList(),
                        homepage = details.homepage,
                        productionCompanies = details
                            .productionCompanies.takeIf { productionCompanies ->
                                productionCompanies.isNotEmpty()
                            }?.reduceToText(),
                        movieTitle = details.title,
                        movieOverview = details.overview,
                        movieLanguage = details.language,
                        moviePopularity = details.popularity,
                        movieVotesAverage = details.votesAverage,
                        imageUrl = details.backdropImageUrl,
                    )
                }

                movieDetails = details
            }
        }
    }

    private suspend fun isMovieFavorite(movieTitle: String): Boolean {
        return repository.checkIsAFavoriteMovie(
            movieTitle = movieTitle,
        )
    }

    private suspend fun fetchMoviesDetails(): MovieDetail? {
        return runCatching {
            getMovieDetailsUseCase.execute(
                GetMovieDetailsUseCase.Params(
                    movieId = movieId!!,
                ),
            )
        }.getOrNull()
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
