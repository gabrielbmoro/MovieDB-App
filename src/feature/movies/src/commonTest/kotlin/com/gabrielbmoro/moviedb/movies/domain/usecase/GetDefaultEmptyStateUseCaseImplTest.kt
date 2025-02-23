package com.gabrielbmoro.moviedb.movies.domain.usecase

import com.gabrielbmoro.moviedb.movies.domain.model.FilterMenuItem
import com.gabrielbmoro.moviedb.movies.domain.model.FilterType
import com.gabrielbmoro.moviedb.movies.domain.model.MoviesState
import com.gabrielbmoro.moviedb.movies.domain.usecase.impl.GetFirstMoviesStateUseCaseImpl
import kotlin.test.Test
import kotlin.test.assertEquals

class GetDefaultEmptyStateUseCaseImplTest {

    private val fakeItems: List<FilterType> = listOf(
        FilterType.NowPlaying,
        FilterType.Popular,
    )

    private val useCase = GetFirstMoviesStateUseCaseImpl(
        object : GetFilterTypeOrderUseCase {
            override fun invoke(): List<FilterType> = fakeItems
        },
    )

    @Test
    fun `should return the default empty state`() {
        val expected = MoviesState.Loading(
            listOf(
                FilterMenuItem(
                    type = FilterType.NowPlaying,
                    selected = true,
                ),
                FilterMenuItem(
                    type = FilterType.Popular,
                    selected = false,
                ),
            )
        )

        val result = useCase()

        assertEquals(expected, result)
    }
}
