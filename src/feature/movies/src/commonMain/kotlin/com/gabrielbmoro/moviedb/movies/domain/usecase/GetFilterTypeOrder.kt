package com.gabrielbmoro.moviedb.movies.domain.usecase

import com.gabrielbmoro.moviedb.movies.domain.model.FilterType

interface GetFilterTypeOrder {
    operator fun invoke(): List<FilterType>
}
