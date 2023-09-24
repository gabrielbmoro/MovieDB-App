package com.gabrielbmoro.moviedb.details.domain.usecases

import com.gabrielbmoro.moviedb.domain.model.DataOrException
import com.gabrielbmoro.moviedb.repository.MoviesRepository
import com.gabrielbmoro.moviedb.repository.model.MovieDetail
import javax.inject.Inject

class GetMovieDetailsUseCase @Inject constructor(
    private val repository: MoviesRepository
) {
    suspend operator fun invoke(movieId: Long): DataOrException<MovieDetail?, Exception> {
        return repository.getMovieDetail(movieId)
    }
}