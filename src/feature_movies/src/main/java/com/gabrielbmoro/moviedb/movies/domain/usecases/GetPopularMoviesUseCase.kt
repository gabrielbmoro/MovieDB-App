package com.gabrielbmoro.moviedb.movies.domain.usecases

import androidx.paging.PagingData
import com.gabrielbmoro.moviedb.repository.MoviesRepository
import com.gabrielbmoro.moviedb.repository.model.Movie
import kotlinx.coroutines.flow.Flow

interface GetPopularMoviesUseCase {
    operator fun invoke(): Flow<PagingData<Movie>>
}

class GetPopularMoviesUseCaseImpl constructor(
    private val repository: MoviesRepository
) : GetPopularMoviesUseCase {
    override operator fun invoke() = repository.getPopularMovies()
}
