package com.gabrielbmoro.moviedb.domain.usecases

import com.gabrielbmoro.moviedb.domain.MoviesRepository
import com.gabrielbmoro.moviedb.domain.entities.VideoStream
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.transform

interface GetTrailersUseCase {
    operator fun invoke(movieId: Long): Flow<VideoStream?>
}

class GetTrailersUseCaseImpl(
    private val repository: MoviesRepository
) : GetTrailersUseCase {
    override operator fun invoke(movieId: Long): Flow<VideoStream?> {
        return repository.getVideoStreams(movieId).transform {
            val videoStream = it.firstOrNull { videoStream ->
                videoStream.site == SITE_KEY && videoStream.official && videoStream.type == TYPE_KEY
            }
            emit(videoStream)
        }
    }

    companion object {
        private const val SITE_KEY = "YouTube"
        private const val TYPE_KEY = "Trailer"
    }
}
