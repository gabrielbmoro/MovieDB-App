package com.gabrielbmoro.moviedb.domain.usecases

import com.gabrielbmoro.moviedb.domain.MoviesRepository
import com.gabrielbmoro.moviedb.domain.entities.Movie
import org.koin.core.annotation.Factory
import org.koin.core.annotation.Provided

interface FavoriteMovieUseCase : UseCase<FavoriteMovieUseCase.Params, Unit> {
    data class Params(
        val movieId: Long? = null,
        val movieVotesAverage: Float? = null,
        val moviePosterImageUrl: String? = null,
        val movieBackdropImageUrl: String? = null,
        val movieOverview: String? = null,
        val movieReleaseDate: String? = null,
        val movieLanguage: String? = null,
        val moviePopularity: Float? = null,
        val movieTitle: String,
        val toFavorite: Boolean,
    )
}

@Factory(binds = [FavoriteMovieUseCase::class])
internal class FavoriteMovieUseCaseImpl(
    @Provided private val repository: MoviesRepository,
) : FavoriteMovieUseCase {
    override suspend fun execute(input: FavoriteMovieUseCase.Params) {
        val toFavorite = input.toFavorite
        val movieTitle = input.movieTitle

        if (toFavorite) {
            repository.favorite(
                Movie(
                    id = input.movieId ?: 0L,
                    votesAverage = input.movieVotesAverage ?: 0f,
                    title = input.movieTitle,
                    posterImageUrl = input.moviePosterImageUrl.orEmpty(),
                    backdropImageUrl = input.movieBackdropImageUrl.orEmpty(),
                    overview = input.movieOverview.orEmpty(),
                    releaseDate = input.movieReleaseDate.orEmpty(),
                    isFavorite = true,
                    language = input.movieLanguage.orEmpty(),
                    popularity = input.moviePopularity ?: 0f,
                ),
            )
        } else {
            repository.unFavorite(movieTitle)
        }
    }
}
