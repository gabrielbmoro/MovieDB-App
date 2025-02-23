package com.gabrielbmoro.moviedb.movies.domain.usecase

import com.gabrielbmoro.moviedb.movies.domain.model.FilterMenuItem
import com.gabrielbmoro.moviedb.movies.domain.model.FilterType
import com.gabrielbmoro.moviedb.movies.domain.usecase.impl.GetFilterTypeOrderImpl
import kotlin.test.Test
import kotlin.test.assertEquals

class GetDefaultMenuItemsUseCaseImplTest {

    private val useCase = GetFilterTypeOrderImpl()

    @Test
    fun `should return the default menu items`() {
        val expected = listOf(
            FilterMenuItem(
                selected = true,
                type = FilterType.NowPlaying,
            ),
            FilterMenuItem(
                selected = false,
                type = FilterType.UpComing,
            ),
            FilterMenuItem(
                selected = false,
                type = FilterType.TopRated,
            ),
            FilterMenuItem(
                selected = false,
                type = FilterType.Popular,
            ),
        )

        val result = useCase()

        assertEquals(expected, result)
    }
}
