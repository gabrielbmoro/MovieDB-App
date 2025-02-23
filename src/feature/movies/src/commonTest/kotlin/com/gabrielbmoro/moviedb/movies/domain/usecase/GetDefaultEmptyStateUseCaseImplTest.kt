package com.gabrielbmoro.moviedb.movies.domain.usecase

import com.gabrielbmoro.moviedb.movies.domain.model.FilterMenuItem
import com.gabrielbmoro.moviedb.movies.domain.model.FilterType
import com.gabrielbmoro.moviedb.movies.domain.model.MoviesState
import com.gabrielbmoro.moviedb.movies.domain.usecase.impl.GetFirstMoviesStateUseCaseImpl
import kotlinx.collections.immutable.persistentListOf
import kotlin.test.Test
import kotlin.test.assertEquals

class GetDefaultEmptyStateUseCaseImplTest {

    private val fakeItems: List<FilterMenuItem> = listOf(
        FilterMenuItem(
            selected = true,
            type = FilterType.NowPlaying,
        ),
        FilterMenuItem(
            selected = true,
            type = FilterType.NowPlaying,
        ),
    )
    private val getDefaultMenuItems: GetFilterTypeOrder =
        object : GetFilterTypeOrder {
            override fun invoke(): List<FilterMenuItem> = fakeItems
        }

    private val useCase = GetFirstMoviesStateUseCaseImpl(getDefaultMenuItems)

    @Test
    fun `should return the default empty state`() {
        val expected = MoviesState(
            movieCardInfos = persistentListOf(),
            selectedFilterMenu = FilterType.NowPlaying,
            menuItems = fakeItems,
            isLoading = false,
        )

        val result = useCase()

        assertEquals(expected, result)
    }
}
