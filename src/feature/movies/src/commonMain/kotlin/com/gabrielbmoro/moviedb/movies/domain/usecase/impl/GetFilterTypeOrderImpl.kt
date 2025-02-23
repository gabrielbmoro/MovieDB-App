package com.gabrielbmoro.moviedb.movies.domain.usecase.impl

import com.gabrielbmoro.moviedb.movies.domain.model.FilterType
import com.gabrielbmoro.moviedb.movies.domain.usecase.GetFilterTypeOrder

class GetFilterTypeOrderImpl : GetFilterTypeOrder {
    override operator fun invoke(): List<FilterType> = listOf(
        FilterType.NowPlaying,
        FilterType.UpComing,
        FilterType.TopRated,
        FilterType.Popular,
    )
}
