package com.gabrielbmoro.moviedb.movies.domain.usecase.getmovies

import com.gabrielbmoro.moviedb.domain.MoviesRepository
import com.gabrielbmoro.moviedb.domain.entities.Movie
import com.gabrielbmoro.moviedb.movies.domain.model.FilterType

class GetMoviesFromFilterUseCaseImpl(
    private val repository: MoviesRepository,
): GetMoviesFromFilterUseCase {

    override suspend fun execute(input: GetMoviesFromFilterUseCase.Params): List<Movie> = input.run {
        repository.getMoviesFromFilter(
            filter = filter.asString(),
            page = page
        )
    }

    private fun FilterType.asString(): String = when (this) {
        FilterType.Popular -> "popular"
        FilterType.TopRated -> "top_rated"
        FilterType.UpComing -> "upcoming"
        FilterType.NowPlaying -> "now_playing"
    }
}
