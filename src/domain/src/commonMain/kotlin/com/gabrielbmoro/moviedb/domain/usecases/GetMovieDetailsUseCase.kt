package com.gabrielbmoro.moviedb.domain.usecases

import com.gabrielbmoro.moviedb.domain.MoviesRepository
import com.gabrielbmoro.moviedb.domain.entities.MovieDetail

interface GetMovieDetailsUseCase : UseCase<GetMovieDetailsUseCase.Params, MovieDetail> {
    data class Params(
        val movieId: Long
    )
}

class GetMovieDetailsUseCaseImpl(
    private val repository: MoviesRepository
) : GetMovieDetailsUseCase {
    override suspend fun execute(input: GetMovieDetailsUseCase.Params): MovieDetail {
        val movieDetail = repository.getMovieDetail(input.movieId)

        val videoStream =
            repository.getVideoStreams(input.movieId).firstOrNull { videoStream ->
                videoStream.site == SITE_KEY && videoStream.official && videoStream.type == TYPE_KEY
            }

        return movieDetail.copy(
            videoId = videoStream?.key
        )
    }

    companion object {
        private const val SITE_KEY = "YouTube"
        private const val TYPE_KEY = "Trailer"
    }
}
