package com.gabrielbmoro.programmingchallenge.usecases

import com.gabrielbmoro.programmingchallenge.repository.MoviesRepository
import com.gabrielbmoro.programmingchallenge.repository.entities.PageMovies

class GetTopRatedMoviesUseCase(val repository: MoviesRepository) {

    suspend fun execute(pageNumber: Int): PageMovies {
        return repository.getTopRatedMovies(pageNumber)
    }
}