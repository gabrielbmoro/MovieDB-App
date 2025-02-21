package com.gabrielbmoro.moviedb.movies.domain.usecase

import com.gabrielbmoro.moviedb.movies.domain.model.FilterMenuItem
import com.gabrielbmoro.moviedb.movies.domain.model.FilterType

interface GetDefaultMenuItemsUseCase {
    operator fun invoke(): List<FilterMenuItem>
}

class GetDefaultMenuItemsUseCaseImpl : GetDefaultMenuItemsUseCase {
    override operator fun invoke(): List<FilterMenuItem> = listOf(
        FilterMenuItem(
            selected = true,
            type = FilterType.NowPlaying
        ),
        FilterMenuItem(
            selected = false,
            type = FilterType.UpComing
        ),
        FilterMenuItem(
            selected = false,
            type = FilterType.TopRated
        ),
        FilterMenuItem(
            selected = false,
            type = FilterType.Popular
        )
    )
}
