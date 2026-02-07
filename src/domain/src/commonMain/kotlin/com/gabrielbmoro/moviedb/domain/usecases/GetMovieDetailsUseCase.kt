package com.gabrielbmoro.moviedb.domain.usecases

import com.gabrielbmoro.moviedb.domain.MoviesRepository
import com.gabrielbmoro.moviedb.domain.entities.MovieDetail

interface GetMovieDetailsUseCase : UseCase<GetMovieDetailsUseCase.Params, Result<MovieDetail>> {
    data class Params(
        val movieId: Long,
    )
}

internal class GetMovieDetailsUseCaseImpl(
    private val repository: MoviesRepository,
) : GetMovieDetailsUseCase {
    override suspend fun execute(input: GetMovieDetailsUseCase.Params): Result<MovieDetail> {
        return runCatching {
            val movieDetail = repository.getMovieDetail(input.movieId)
                .getOrThrow()
            val videoStream = repository.getVideoStreams(input.movieId)
                .getOrNull()
                ?.firstOrNull { videoStream ->
                    videoStream.site == SITE_KEY && videoStream.official && videoStream.type == TYPE_KEY
                }

            movieDetail.copy(
                videoId = videoStream?.key,
            )
        }
    }

    companion object {
        private const val SITE_KEY = "YouTube"
        private const val TYPE_KEY = "Trailer"
    }
}
