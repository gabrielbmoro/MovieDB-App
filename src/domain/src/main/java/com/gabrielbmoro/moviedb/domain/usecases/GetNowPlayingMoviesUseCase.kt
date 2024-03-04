package com.gabrielbmoro.moviedb.domain.usecases

import androidx.paging.PagingData
import com.gabrielbmoro.moviedb.domain.MoviesRepository
import com.gabrielbmoro.moviedb.domain.entities.Movie
import kotlinx.coroutines.flow.Flow

interface GetNowPlayingMoviesUseCase : UseCase<Unit, Flow<PagingData<Movie>>>

class GetNowPlayingMoviesUseCaseImpl(
    private val repository: MoviesRepository
) : GetNowPlayingMoviesUseCase {
    override suspend fun execute(input: Unit): Flow<PagingData<Movie>> {
        return repository.getNowPlayingMovies()
    }
}
