package com.gabrielbmoro.programmingchallenge.usecases

import com.gabrielbmoro.programmingchallenge.repository.MoviesRepository
import com.gabrielbmoro.programmingchallenge.repository.entities.Page
import com.gabrielbmoro.programmingchallenge.usecases.mappers.PageMapper
import javax.inject.Inject

class GetPopularMoviesUseCase @Inject constructor(
    private val repository: MoviesRepository,
    private val mapper: PageMapper
) {

    suspend fun execute(pageNumber: Int): Page {
        val pageResponse = repository.getPopularMovies(pageNumber)
        return mapper.map(pageResponse)
    }
}