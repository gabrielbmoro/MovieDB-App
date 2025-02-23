package com.gabrielbmoro.moviedb.movies.domain.usecase

import com.gabrielbmoro.moviedb.movies.domain.model.FilterType

interface GetFilterTypeOrderUseCase {
    operator fun invoke(): List<FilterType>
}
