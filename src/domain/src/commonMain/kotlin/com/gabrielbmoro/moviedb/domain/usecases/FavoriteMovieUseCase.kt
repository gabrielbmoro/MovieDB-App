package com.gabrielbmoro.moviedb.domain.usecases

import com.gabrielbmoro.moviedb.domain.MoviesRepository
import com.gabrielbmoro.moviedb.domain.entities.Movie
import org.koin.core.annotation.Factory

interface FavoriteMovieUseCase : UseCase<FavoriteMovieUseCase.Params, Result<Unit>> {
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
    private val repository: MoviesRepository,
) : FavoriteMovieUseCase {
    override suspend fun execute(input: FavoriteMovieUseCase.Params): Result<Unit> {
        return runCatching {
            val toFavorite = input.toFavorite
            val movieTitle = input.movieTitle
            val isFavorite = repository.checkIsAFavoriteMovie(movieTitle).getOrThrow()
            when {
                (toFavorite && isFavorite.not()) -> {
                    val movie =
                        Movie(
                            id = input.movieId!!,
                            votesAverage = input.movieVotesAverage!!,
                            title = input.movieTitle,
                            posterImageUrl = input.moviePosterImageUrl!!,
                            backdropImageUrl = input.movieBackdropImageUrl!!,
                            overview = input.movieOverview!!,
                            releaseDate = input.movieReleaseDate!!,
                            isFavorite = true,
                            language = input.movieLanguage!!,
                            popularity = input.moviePopularity!!,
                        )
                    repository.favorite(movie = movie)
                }

                (!toFavorite && isFavorite) -> {
                    repository.unFavorite(movieTitle)
                }
            }
        }
    }
}
