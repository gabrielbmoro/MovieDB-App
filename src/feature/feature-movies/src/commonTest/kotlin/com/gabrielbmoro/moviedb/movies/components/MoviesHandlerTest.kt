package com.gabrielbmoro.moviedb.movies.components

import MoviesHandler
import com.gabrielbmoro.moviedb.domain.entities.Movie
import com.gabrielbmoro.moviedb.movies.model.FilterMenuItem
import com.gabrielbmoro.moviedb.movies.model.FilterType
import com.gabrielbmoro.moviedb.movies.model.MoviesState
import kotlinx.collections.immutable.persistentListOf
import kotlinx.coroutines.test.runTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class MoviesHandlerTest {

    lateinit var repository: FakeRepository

    @BeforeTest
    fun before() {
        repository = FakeRepository()
    }

    @Test
    fun `should return the movies from filter`() = runTest {
        repository.filteredMovies = listOf(
            Movie.mockWhyDoWeUseItFavorite(),
        )
        val handler = MoviesHandler(
            repository = repository,
        )
        val result = handler.getMoviesFromFilter(
            filter = FilterType.NowPlaying,
            page = 1,
        )

        assertTrue {
            result.contains(Movie.mockWhyDoWeUseItFavorite())
        }
    }

    @Test
    fun `should return the default empty state`() {
        val handler = MoviesHandler(
            repository = repository,
        )
        val expected = MoviesState(
            movieCardInfos = persistentListOf(),
            selectedFilterMenu = FilterType.NowPlaying,
            menuItems = handler.menuItems,
            isLoading = false,
        )

        val result = handler.defaultEmptyState

        assertEquals(expected, result)
    }

    @Test
    fun `should return the default menu items`() {
        val handler = MoviesHandler(
            repository = repository,
        )
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

        val result = handler.menuItems

        assertEquals(expected, result)
    }
}
