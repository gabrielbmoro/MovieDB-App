package com.gabrielbmoro.moviedb.details.domain.usecases

import com.gabrielbmoro.moviedb.repository.MoviesRepository
import com.gabrielbmoro.moviedb.repository.model.MovieDetail
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.zip
import javax.inject.Inject

@ViewModelScoped
class GetMovieDetailsUseCase @Inject constructor(
    private val repository: MoviesRepository,
    private val getTrailersUseCase: GetTrailersUseCase
) {

    operator fun invoke(movieId: Long): Flow<MovieDetail> = repository.getMovieDetail(movieId)
        .zip(
            other = getTrailersUseCase(movieId),
            transform = { f1, f2 ->
                f1.copy(
                    videoId = f2?.key
                )
            }
        )
}
