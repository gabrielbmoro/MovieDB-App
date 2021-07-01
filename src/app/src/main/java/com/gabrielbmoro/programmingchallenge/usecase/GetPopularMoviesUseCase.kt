package com.gabrielbmoro.programmingchallenge.usecase

import com.gabrielbmoro.programmingchallenge.repository.MoviesRepository
import com.gabrielbmoro.programmingchallenge.repository.entities.PageMovies

class GetPopularMoviesUseCase(val repository: MoviesRepository) {

    suspend fun execute(pageNumber: Int): PageMovies {
        return repository.getPopularMovies(pageNumber)
    }
}