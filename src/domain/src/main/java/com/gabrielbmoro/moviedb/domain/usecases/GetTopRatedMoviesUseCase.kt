package com.gabrielbmoro.moviedb.domain.usecases

import androidx.paging.PagingData
import com.gabrielbmoro.moviedb.domain.MoviesRepository
import com.gabrielbmoro.moviedb.domain.entities.Movie
import kotlinx.coroutines.flow.Flow

interface GetTopRatedMoviesUseCase : UseCase<Unit, Flow<PagingData<Movie>>>

class GetTopRatedMoviesUseCaseImpl(
    private val repository: MoviesRepository
) : GetTopRatedMoviesUseCase {
    override suspend fun execute(input: Unit): Flow<PagingData<Movie>> {
        return repository.getTopRatedMovies()
    }
}
