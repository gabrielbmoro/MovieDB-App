package com.gabrielbmoro.moviedb.movies.domain.usecase

import com.gabrielbmoro.moviedb.movies.domain.model.MoviesState

interface GetFirstMoviesStateUseCase {
    operator fun invoke(): MoviesState
}
