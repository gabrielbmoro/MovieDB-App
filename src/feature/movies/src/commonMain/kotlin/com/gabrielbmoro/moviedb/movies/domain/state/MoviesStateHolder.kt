package com.gabrielbmoro.moviedb.movies.domain.state

import com.gabrielbmoro.moviedb.movies.domain.model.MoviesState
import com.gabrielbmoro.moviedb.platform.StateHolder

abstract class MoviesStateHolder(firstState: MoviesState) :
    StateHolder<MoviesState, MoviesState.Success>(firstState)
