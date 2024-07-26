package com.gabrielbmoro.moviedb.details.ui.screens.details

import com.gabrielbmoro.moviedb.domain.entities.MovieDetail
import com.gabrielbmoro.moviedb.domain.usecases.FavoriteMovieUseCase
import com.gabrielbmoro.moviedb.domain.usecases.GetMovieDetailsUseCase
import com.gabrielbmoro.moviedb.domain.usecases.IsFavoriteMovieUseCase
import com.gabrielbmoro.moviedb.platform.mvi.ViewModelMVI

class DetailsViewModel(
    private val favoriteMovieUseCase: FavoriteMovieUseCase,
    private val isFavoriteMovieUseCase: IsFavoriteMovieUseCase,
    private val getMovieDetailsUseCase: GetMovieDetailsUseCase
) : ViewModelMVI<DetailsUserIntent, DetailsUIState>() {

    lateinit var movieDetails: MovieDetail
    private var movieId: Long? = null

    suspend fun setup(movieId: Long): DetailsUIState {
        this.movieId = movieId

        updateState(
            uiState.value.copy(
                isLoading = true
            )
        )

        movieDetails = fetchMoviesDetails()

        val isMovieFavorite = isMovieFavorite(movieTitle = movieDetails.title)

        return uiState.value.copy(
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
                favoriteMovieUseCase.execute(params)

                val result =
                    isFavoriteMovieUseCase.execute(
                        IsFavoriteMovieUseCase.Params(
                            movieTitle = movieDetails.title
                        )
                    )
                getState().copy(
                    isFavorite = result
                )
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
