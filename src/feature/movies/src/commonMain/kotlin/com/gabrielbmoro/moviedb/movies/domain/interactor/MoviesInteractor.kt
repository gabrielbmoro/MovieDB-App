package com.gabrielbmoro.moviedb.movies.domain.interactor

import com.gabrielbmoro.moviedb.movies.domain.usecase.FetchFirstPageUseCase
import com.gabrielbmoro.moviedb.movies.domain.usecase.OnEndScrollUseCase
import com.gabrielbmoro.moviedb.movies.domain.usecase.OnSelectFilterUseCase

class MoviesInteractor(
    val onEndScroll: OnEndScrollUseCase,
    val onSelectFilter: OnSelectFilterUseCase,
    val fetchFirstPage: FetchFirstPageUseCase,
)
