package com.gabrielbmoro.moviedb.wishlist.domain.usecases

import com.gabrielbmoro.moviedb.repository.MoviesRepository
import javax.inject.Inject

interface DeleteMovieUseCase {
    suspend operator fun invoke(movieTitle: String): Boolean?
}

class DeleteMovieUseCaseImpl @Inject constructor(
    private val repository: MoviesRepository
) : DeleteMovieUseCase {
    override suspend fun invoke(movieTitle: String): Boolean? {
        return repository.unFavorite(movieTitle).data
    }
}