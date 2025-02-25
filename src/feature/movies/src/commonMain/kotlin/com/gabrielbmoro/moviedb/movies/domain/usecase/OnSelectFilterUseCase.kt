package com.gabrielbmoro.moviedb.movies.domain.usecase

import com.gabrielbmoro.moviedb.movies.domain.model.FilterType

interface OnSelectFilterUseCase {
    suspend operator fun invoke(selectedFilterType: FilterType)
}
