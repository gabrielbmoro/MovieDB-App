package com.gabrielbmoro.moviedb.domain.usecases

import com.gabrielbmoro.moviedb.domain.MoviesRepository
import com.gabrielbmoro.moviedb.domain.entities.MovieDetail
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.zip

interface GetMovieDetailsUseCase {
    operator fun invoke(movieId: Long): Flow<MovieDetail>
}

class GetMovieDetailsUseCaseImpl(
    private val repository: MoviesRepository,
    private val getTrailersUseCase: GetTrailersUseCase
) : GetMovieDetailsUseCase {

    override operator fun invoke(movieId: Long): Flow<MovieDetail> = repository.getMovieDetail(movieId)
        .zip(
            other = getTrailersUseCase(movieId),
            transform = { f1, f2 ->
                f1.copy(
                    videoId = f2?.key
                )
            }
        )
}
