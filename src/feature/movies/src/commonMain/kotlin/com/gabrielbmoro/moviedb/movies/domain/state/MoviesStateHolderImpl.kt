package com.gabrielbmoro.moviedb.movies.domain.state

import com.gabrielbmoro.moviedb.movies.domain.usecase.GetFirstMoviesStateUseCase

class MoviesStateHolderImpl(
    getFirstMoviesState: GetFirstMoviesStateUseCase,
): MoviesStateHolder(getFirstMoviesState())
