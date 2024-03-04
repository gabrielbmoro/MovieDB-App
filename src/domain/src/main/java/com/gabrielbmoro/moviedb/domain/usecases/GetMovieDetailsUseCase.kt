package com.gabrielbmoro.moviedb.domain.usecases

import com.gabrielbmoro.moviedb.domain.MoviesRepository
import com.gabrielbmoro.moviedb.domain.entities.MovieDetail

interface GetMovieDetailsUseCase {
    suspend operator fun invoke(movieId: Long): MovieDetail
}

class GetMovieDetailsUseCaseImpl(
    private val repository: MoviesRepository,
) : GetMovieDetailsUseCase {

    override suspend operator fun invoke(movieId: Long): MovieDetail {

        val movieDetail = repository.getMovieDetail(movieId)

        val videoStream = repository.getVideoStreams(movieId).firstOrNull { videoStream ->
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
