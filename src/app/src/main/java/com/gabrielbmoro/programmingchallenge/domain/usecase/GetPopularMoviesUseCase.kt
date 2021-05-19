package com.gabrielbmoro.programmingchallenge.domain.usecase

import com.gabrielbmoro.programmingchallenge.domain.model.Page
import com.gabrielbmoro.programmingchallenge.domain.usecase.mapper.MoviesMapper
import com.gabrielbmoro.programmingchallenge.repository.MoviesRepository

class GetPopularMoviesUseCase(val repository: MoviesRepository) {

    suspend fun execute(pageNumber: Int): Page? {
        return MoviesMapper.mapToPage(
                repository.getPopularMovies(pageNumber)
        )
    }
}