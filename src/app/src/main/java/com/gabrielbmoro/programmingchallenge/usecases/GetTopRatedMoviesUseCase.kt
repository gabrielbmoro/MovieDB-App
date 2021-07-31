package com.gabrielbmoro.programmingchallenge.usecases

import com.gabrielbmoro.programmingchallenge.repository.MoviesRepository
import com.gabrielbmoro.programmingchallenge.repository.entities.Page
import com.gabrielbmoro.programmingchallenge.usecases.mappers.toPage
import javax.inject.Inject

class GetTopRatedMoviesUseCase @Inject constructor(val repository: MoviesRepository) {

    suspend fun execute(pageNumber: Int): Page {
        return repository.getTopRatedMovies(pageNumber).toPage()
    }
}