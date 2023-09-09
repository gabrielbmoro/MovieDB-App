package com.gabrielbmoro.moviedb.details.domain.usecases

import com.gabrielbmoro.moviedb.domain.model.DataOrException
import com.gabrielbmoro.moviedb.repository.MoviesRepository
import com.gabrielbmoro.moviedb.repository.model.VideoStream

class GetTrailersUseCase(
    private val repository: MoviesRepository
) {
    suspend operator fun invoke(movieId: Long): DataOrException<VideoStream?, Exception> {
        val result = repository.getVideoStreams(movieId)
        val trailer = result.data?.firstOrNull {
            it.site == SITE_KEY && it.official && it.type == TYPE_KEY
        }

        return DataOrException(trailer, result.exception)
    }

    companion object {
        private const val SITE_KEY = "YouTube"
        private const val TYPE_KEY = "Trailer"
    }
}