package com.gabrielbmoro.moviedb.movies.components

import MoviesHandler
import com.gabrielbmoro.moviedb.domain.model.Movie
import com.gabrielbmoro.moviedb.movies.fakes.FakeRepository
import com.gabrielbmoro.moviedb.movies.ui.screens.movies.FilterType
import kotlinx.coroutines.test.runTest
import kotlin.test.BeforeTest
import kotlin.test.Test
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
}
