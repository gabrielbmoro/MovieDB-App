package com.gabrielbmoro.moviedb.movies.domain.usecase

import com.gabrielbmoro.moviedb.movies.domain.model.FilterType
import com.gabrielbmoro.moviedb.movies.domain.usecase.impl.GetFilterTypeOrderUseCaseImpl
import kotlin.test.Test
import kotlin.test.assertEquals

class GetDefaultMenuItemsUseCaseImplTest {

    private val useCase = GetFilterTypeOrderUseCaseImpl()

    @Test
    fun `should return the default menu items`() {
        val expected = listOf(
            FilterType.NowPlaying,
            FilterType.UpComing,
            FilterType.TopRated,
            FilterType.Popular,
        )

        val result = useCase()

        assertEquals(expected, result)
    }
}
