package com.gabrielbmoro.moviedb.wishlist.domain.usecases

import com.gabrielbmoro.moviedb.repository.MoviesRepository

interface DeleteMovieUseCase {
    suspend operator fun invoke(movieTitle: String): Boolean?
}

class DeleteMovieUseCaseImpl(
    private val repository: MoviesRepository
) : DeleteMovieUseCase {
    override suspend fun invoke(movieTitle: String): Boolean? {
        return repository.unFavorite(movieTitle).data
    }
}