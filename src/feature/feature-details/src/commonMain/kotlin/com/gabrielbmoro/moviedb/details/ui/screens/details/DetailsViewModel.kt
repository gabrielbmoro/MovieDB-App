package com.gabrielbmoro.moviedb.details.ui.screens.details

import androidx.lifecycle.viewModelScope
import com.gabrielbmoro.moviedb.domain.MoviesRepository
import com.gabrielbmoro.moviedb.domain.model.MovieDetail
import com.gabrielbmoro.moviedb.domain.usecases.FavoriteMovieUseCase
import com.gabrielbmoro.moviedb.domain.usecases.GetMovieDetailsUseCase
import com.gabrielbmoro.moviedb.platform.viewmodel.BaseViewModel
import com.gabrielbmoro.moviedb.platform.viewmodel.UiEvent
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.async

class DetailsViewModel(
    private val repository: MoviesRepository,
    private val favoriteMovieUseCase: FavoriteMovieUseCase,
    private val getMovieDetailsUseCase: GetMovieDetailsUseCase,
    private val ioDispatcher: CoroutineDispatcher,
) : BaseViewModel<DetailsUIState, DetailsUserIntent, UiEvent>(
    ioDispatcher,
) {

    private var movieDetails: MovieDetail? = null
    private var movieId: Long? = null

    override fun defaultEmptyState() = DetailsUIState.empty()

    override fun onFailure(throwable: Throwable) = Unit

    override fun executeIntent(intent: DetailsUserIntent) {
        when (intent) {
            is DetailsUserIntent.HideVideo -> launchIo {
                hideVideo()
            }

            is DetailsUserIntent.FavoriteMovie -> launchIo {
                favoriteMovie()
            }

            is DetailsUserIntent.LoadMovieDetails -> launchIo {
                loadMovieDetails(intent)
            }
        }
    }

    private suspend fun hideVideo() {
        updateState {
            it.copy(
                showVideo = false,
            )
        }
    }

    private suspend fun favoriteMovie() {
        val value = uiState.value.isFavorite
        val desiredValue = value.not()

        movieDetails?.toFavoriteUseCaseParam(
            movieId = movieId!!,
            desiredValue = desiredValue,
        )?.let { params ->
            favoriteMovieUseCase.execute(params)

            repository.checkIsAFavoriteMovie(
                movieTitle = movieDetails!!.title,
            ).let { result ->
                updateState {
                    it.copy(
                        isFavorite = result,
                    )
                }
            }
        }
    }

    private suspend fun loadMovieDetails(intent: DetailsUserIntent.LoadMovieDetails) {
        this.movieId = intent.movieId

        updateState {
            it.copy(isLoading = true)
        }

        val details = fetchMoviesDetails() ?: return
        val isMovieFavorite = viewModelScope.async(ioDispatcher) {
            isMovieFavorite(movieTitle = details.title)
        }.await()

        updateState {
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
