package com.gabrielbmoro.moviedb.movies.domain.usecase

import com.gabrielbmoro.moviedb.movies.domain.model.FilterMenuItem
import com.gabrielbmoro.moviedb.movies.domain.model.FilterType
import com.gabrielbmoro.moviedb.movies.domain.model.MoviesUIState
import kotlinx.collections.immutable.persistentListOf
import kotlin.test.Test
import kotlin.test.assertEquals

class GetDefaultEmptyStateUseCaseImplTest {

    private val fakeItems: List<FilterMenuItem> = listOf(
        FilterMenuItem(
            selected = true,
            type = FilterType.NowPlaying
        ),
        FilterMenuItem(
            selected = true,
            type = FilterType.NowPlaying
        ),
    )
    private val getDefaultMenuItems: GetDefaultMenuItemsUseCase =
        object : GetDefaultMenuItemsUseCase {
            override fun invoke(): List<FilterMenuItem> = fakeItems
        }

    private val useCase = GetDefaultEmptyStateUseCaseImpl(getDefaultMenuItems)

    @Test
    fun `should return the default empty state`() {
        val expected = MoviesUIState(
            movieCardInfos = persistentListOf(),
            selectedFilterMenu = FilterType.NowPlaying,
            menuItems = fakeItems,
        )

        val result = useCase()

        assertEquals(expected, result)
    }

}
