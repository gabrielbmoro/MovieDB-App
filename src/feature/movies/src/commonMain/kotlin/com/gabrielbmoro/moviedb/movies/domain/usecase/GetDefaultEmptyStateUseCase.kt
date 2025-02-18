package com.gabrielbmoro.moviedb.movies.domain.usecase

import com.gabrielbmoro.moviedb.movies.domain.model.FilterType
import com.gabrielbmoro.moviedb.movies.domain.model.MoviesState
import kotlinx.collections.immutable.persistentListOf

interface GetDefaultEmptyStateUseCase {
    operator fun invoke(): MoviesState
}

class GetDefaultEmptyStateUseCaseImpl(
    private val getDefaultMenuItems: GetDefaultMenuItemsUseCase
) : GetDefaultEmptyStateUseCase {
    override operator fun invoke(): MoviesState = MoviesState(
        movieCardInfos = persistentListOf(),
        selectedFilterMenu = FilterType.NowPlaying,
        menuItems = getDefaultMenuItems(),
        isLoading = false,
    )
}
