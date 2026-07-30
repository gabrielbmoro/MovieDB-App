package com.gabrielbmoro.moviedb.feature.tvshows.components

import com.gabrielbmoro.moviedb.domain.model.TvShow
import com.gabrielbmoro.moviedb.feature.tvshows.fakes.FakeTvShowsRepository
import com.gabrielbmoro.moviedb.feature.tvshows.ui.screens.tvshows.TvShowFilterType
import kotlinx.coroutines.test.runTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertTrue

class TvShowsHandlerTest {

    lateinit var repository: FakeTvShowsRepository

    @BeforeTest
    fun before() {
        repository = FakeTvShowsRepository()
    }

    @Test
    fun `should return the tv shows from filter`() = runTest {
        repository.filteredTvShows = listOf(
            TvShow.mockBreakingBad(),
        )
        val handler = TvShowsHandler(
            repository = repository,
        )
        val result = handler.getTvShowsFromFilter(
            filter = TvShowFilterType.Popular,
            page = 1,
        )

        assertTrue {
            result.contains(TvShow.mockBreakingBad())
        }
    }
}
