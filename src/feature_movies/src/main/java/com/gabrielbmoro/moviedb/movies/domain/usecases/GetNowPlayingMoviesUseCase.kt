package com.gabrielbmoro.moviedb.movies.domain.usecases

import androidx.paging.PagingData
import com.gabrielbmoro.moviedb.repository.MoviesRepository
import com.gabrielbmoro.moviedb.repository.model.Movie
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface GetNowPlayingMoviesUseCase {
    operator fun invoke(): Flow<PagingData<Movie>>
}

@ViewModelScoped
class GetNowPlayingMoviesUseCaseImpl @Inject constructor(
    private val repository: MoviesRepository
) : GetNowPlayingMoviesUseCase {
    override operator fun invoke() = repository.getNowPlayingMovies()
}
