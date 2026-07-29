package com.gabrielbmoro.moviedb.feature.tvshows.components

import com.gabrielbmoro.moviedb.domain.TvShowsRepository
import com.gabrielbmoro.moviedb.domain.model.TvShow
import com.gabrielbmoro.moviedb.feature.tvshows.ui.screens.tvshows.TvShowFilterType

class TvShowsHandler(
    private val repository: TvShowsRepository,
) {
    suspend fun getTvShowsFromFilter(filter: TvShowFilterType, page: Int): List<TvShow> {
        return repository.getTvShowsFromFilter(
            filter = filter.asString(),
            page = page,
        )
    }

    private fun TvShowFilterType.asString(): String = when (this) {
        TvShowFilterType.Popular -> "popular"
        TvShowFilterType.TopRated -> "top_rated"
        TvShowFilterType.OnTheAir -> "on_the_air"
        TvShowFilterType.AiringToday -> "airing_today"
    }
}
