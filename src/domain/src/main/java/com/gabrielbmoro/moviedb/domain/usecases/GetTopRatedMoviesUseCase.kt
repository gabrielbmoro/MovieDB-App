package com.gabrielbmoro.moviedb.domain.usecases

import androidx.paging.PagingData
import com.gabrielbmoro.moviedb.domain.MoviesRepository
import com.gabrielbmoro.moviedb.domain.entities.Movie
import kotlinx.coroutines.flow.Flow

interface GetTopRatedMoviesUseCase {
    operator fun invoke(): Flow<PagingData<Movie>>
}

class GetTopRatedMoviesUseCaseImpl(
    private val repository: MoviesRepository
) : GetTopRatedMoviesUseCase {

    override operator fun invoke() = repository.getTopRatedMovies()
}
