package com.gabrielbmoro.moviedb.movies.domain.usecase

import com.gabrielbmoro.moviedb.movies.domain.model.FilterType

interface UpdateToLoadedFromFilterUseCase {
    suspend operator fun invoke(selectedFilterType: FilterType)
}
