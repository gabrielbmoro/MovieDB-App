package com.gabrielbmoro.moviedb.movies.domain.usecases

import androidx.paging.PagingData
import com.gabrielbmoro.moviedb.repository.MoviesRepository
import com.gabrielbmoro.moviedb.repository.model.Movie
import kotlinx.coroutines.flow.Flow

interface GetTopRatedMoviesUseCase {
    operator fun invoke(): Flow<PagingData<Movie>>
}

class GetTopRatedMoviesUseCaseImpl(
    private val repository: MoviesRepository
) : GetTopRatedMoviesUseCase {

    override operator fun invoke() = repository.getTopRatedMovies()
}
