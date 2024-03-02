package com.gabrielbmoro.moviedb.domain.usecases

import androidx.paging.PagingData
import com.gabrielbmoro.moviedb.domain.MoviesRepository
import com.gabrielbmoro.moviedb.domain.entities.Movie
import kotlinx.coroutines.flow.Flow

interface GetUpcomingMoviesUseCase {
    operator fun invoke(): Flow<PagingData<Movie>>
}

class GetUpcomingMoviesUseCaseImpl(
    private val repository: MoviesRepository
) : GetUpcomingMoviesUseCase {

    override operator fun invoke() = repository.getUpcomingMovies()
}
