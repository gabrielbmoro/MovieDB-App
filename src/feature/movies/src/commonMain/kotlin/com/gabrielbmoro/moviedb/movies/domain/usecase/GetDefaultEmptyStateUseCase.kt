package com.gabrielbmoro.moviedb.movies.domain.usecase

import com.gabrielbmoro.moviedb.movies.domain.model.FilterType
import com.gabrielbmoro.moviedb.movies.domain.model.MoviesUIState
import kotlinx.collections.immutable.persistentListOf

interface GetDefaultEmptyStateUseCase {
    operator fun invoke(): MoviesUIState
}

class GetDefaultEmptyStateUseCaseImpl(
    private val getDefaultMenuItems: GetDefaultMenuItemsUseCase
) : GetDefaultEmptyStateUseCase {
    override operator fun invoke(): MoviesUIState = MoviesUIState(
        movieCardInfos = persistentListOf(),
        selectedFilterMenu = FilterType.NowPlaying,
        menuItems = getDefaultMenuItems()
    )
}

