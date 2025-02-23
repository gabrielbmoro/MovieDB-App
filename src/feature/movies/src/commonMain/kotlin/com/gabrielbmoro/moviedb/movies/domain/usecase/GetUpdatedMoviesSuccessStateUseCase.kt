package com.gabrielbmoro.moviedb.movies.domain.usecase

import com.gabrielbmoro.moviedb.domain.entities.Movie
import com.gabrielbmoro.moviedb.domain.usecases.UseCase
import com.gabrielbmoro.moviedb.movies.domain.model.FilterType
import com.gabrielbmoro.moviedb.movies.domain.model.MoviesState

interface GetUpdatedMoviesSuccessStateUseCase :
    UseCase<GetUpdatedMoviesSuccessStateUseCase.Params, MoviesState.Success> {

    data class Params(
        val filterType: FilterType,
        val currentState: MoviesState,
        val requestedMoreMovies: List<Movie>,
    )
}
