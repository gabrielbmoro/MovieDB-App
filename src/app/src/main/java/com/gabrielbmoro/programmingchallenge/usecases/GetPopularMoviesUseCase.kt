package com.gabrielbmoro.programmingchallenge.usecases

import com.gabrielbmoro.programmingchallenge.repository.MoviesRepository
import com.gabrielbmoro.programmingchallenge.repository.entities.PageMovies
import javax.inject.Inject

class GetPopularMoviesUseCase @Inject constructor(val repository: MoviesRepository) {

    suspend fun execute(pageNumber: Int): PageMovies {
        return repository.getPopularMovies(pageNumber)
    }
}