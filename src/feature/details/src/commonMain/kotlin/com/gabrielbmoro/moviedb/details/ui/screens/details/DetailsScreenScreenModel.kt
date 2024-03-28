package com.gabrielbmoro.moviedb.details.ui.screens.details

import com.gabrielbmoro.moviedb.domain.entities.Movie
import com.gabrielbmoro.moviedb.domain.entities.MovieDetail
import com.gabrielbmoro.moviedb.domain.usecases.FavoriteMovieUseCase
import com.gabrielbmoro.moviedb.domain.usecases.GetMovieDetailsUseCase
import com.gabrielbmoro.moviedb.domain.usecases.IsFavoriteMovieUseCase
import com.gabrielbmoro.moviedb.platform.mvi.ScreenModelMVI

class DetailsScreenScreenModel(
    private val movie: Movie,
    private val favoriteMovieUseCase: FavoriteMovieUseCase,
    private val isFavoriteMovieUseCase: IsFavoriteMovieUseCase,
    private val getMovieDetailsUseCase: GetMovieDetailsUseCase,
) : ScreenModelMVI<DetailsUserIntent, DetailsUIState>() {
    override suspend fun setup(): DetailsUIState {
        updateState(
            uiState.value.copy(
                isLoading = true,
            ),
        )

        val isMovieFavorite = isMovieFavorite(movieTitle = movie.title)

        val movieDetails = fetchMoviesDetails()

        return uiState.value.copy(
            isLoading = false,
            isFavorite = isMovieFavorite,
            videoId = movieDetails.videoId,
            tagLine = movieDetails.tagline,
            status = movieDetails.status,
            genres = movieDetails.genres,
            homepage = movieDetails.homepage,
            productionCompanies = movieDetails.productionCompanies.reduceToText(),
            movieTitle = movie.title,
            movieOverview = movie.overview,
            movieLanguage = movie.language,
            moviePopularity = movie.popularity,
            movieVotesAverage = movie.votesAverage,
            imageUrl = movie.backdropImageUrl,
        )
    }

    override fun defaultEmptyState() = DetailsUIState.empty()

    override suspend fun execute(intent: DetailsUserIntent): DetailsUIState {
        return when (intent) {
            is DetailsUserIntent.HideVideo -> {
                getState().copy(
                    showVideo = false,
                )
            }

            is DetailsUserIntent.FavoriteMovie -> {
                val value = getState().isFavorite
                val desiredValue = value.not()

                val params =
                    FavoriteMovieUseCase.Params(
                        movieTitle = movie.title,
                        movieLanguage = movie.language,
                        movieVotesAverage = movie.votesAverage,
                        movieReleaseDate = movie.releaseDate,
                        moviePosterImageUrl = movie.posterImageUrl,
                        moviePopularity = movie.popularity,
                        movieOverview = movie.overview,
                        movieId = movie.id,
                        movieBackdropImageUrl = movie.backdropImageUrl,
                        toFavorite = desiredValue,
                    )
                favoriteMovieUseCase.execute(params)

                val result =
                    isFavoriteMovieUseCase.execute(
                        IsFavoriteMovieUseCase.Params(
                            movieTitle = movie.title,
                        ),
                    )
                getState().copy(
                    isFavorite = result,
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
                movieId = movie.id,
            ),
        )
    }

    private fun List<String>.reduceToText(): String {
        var acc = ""
        var i = 0
        while (i < size) {
            acc += "$acc, ${get(i)}"
            i++
        }
        return acc
    }
}
