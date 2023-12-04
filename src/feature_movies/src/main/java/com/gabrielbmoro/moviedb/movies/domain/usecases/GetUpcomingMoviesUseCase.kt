package com.gabrielbmoro.moviedb.movies.domain.usecases

import androidx.paging.PagingData
import com.gabrielbmoro.moviedb.repository.MoviesRepository
import com.gabrielbmoro.moviedb.repository.model.Movie
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface GetUpcomingMoviesUseCase {
    operator fun invoke(): Flow<PagingData<Movie>>
}

class GetUpcomingMoviesUseCaseImpl @Inject constructor(
    private val repository: MoviesRepository
) : GetUpcomingMoviesUseCase {

    override operator fun invoke() = repository.getUpcomingMovies()
}
