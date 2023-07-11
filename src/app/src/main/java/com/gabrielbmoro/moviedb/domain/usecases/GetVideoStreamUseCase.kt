package com.gabrielbmoro.moviedb.domain.usecases

import com.gabrielbmoro.moviedb.repository.MoviesRepository

class GetVideoStreamUseCase(
    private val repository: MoviesRepository
) {
    suspend operator fun invoke(movieId: Long) = repository.getVideoStreams(movieId)
}