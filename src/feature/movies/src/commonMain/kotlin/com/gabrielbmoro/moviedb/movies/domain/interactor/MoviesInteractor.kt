package com.gabrielbmoro.moviedb.movies.domain.interactor

import com.gabrielbmoro.moviedb.movies.domain.usecase.FetchFirstPageUseCase
import com.gabrielbmoro.moviedb.movies.domain.usecase.OnRequestMoreMoviesUseCase
import com.gabrielbmoro.moviedb.movies.domain.usecase.OnSelectFilterUseCase

class MoviesInteractor(
    val onRequestMoreMovies: OnRequestMoreMoviesUseCase,
    val onSelectFilter: OnSelectFilterUseCase,
    val fetchFirstPage: FetchFirstPageUseCase,
)
