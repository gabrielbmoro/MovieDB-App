package com.gabrielbmoro.moviedb.movies.domain.usecase.impl

import com.gabrielbmoro.moviedb.domain.entities.Movie
import com.gabrielbmoro.moviedb.movies.domain.model.MoviesState
import com.gabrielbmoro.moviedb.movies.domain.usecase.GetFirstSuccessStateUseCase
import com.gabrielbmoro.moviedb.movies.domain.usecase.GetUpdatedMoviesSuccessStateUseCase
import com.gabrielbmoro.moviedb.movies.domain.usecase.UpdateSelectedMenuItemUseCase
import com.gabrielbmoro.moviedb.movies.ui.screens.movies.MovieCardInfo
import com.gabrielbmoro.moviedb.platform.Mapper
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toPersistentList

class GetUpdatedMoviesSuccessStateUseCaseImpl(
    private val updateSelectedMenuItemUseCase: UpdateSelectedMenuItemUseCase,
    private val getFirstSuccessStateUseCase: GetFirstSuccessStateUseCase,
    private val movieMapper: Mapper<Movie, MovieCardInfo>,
) : GetUpdatedMoviesSuccessStateUseCase {
    override suspend fun execute(
        input: GetUpdatedMoviesSuccessStateUseCase.Params,
    ): MoviesState.Success = input.run {
        (currentState as? MoviesState.Success)?.run {
            copy(
                movieCardInfos = movieCardInfos.addAllDistinctly(
                    newMovies = requestedMoreMovies.map(movieMapper::map),
                ).toPersistentList(),
                menuItems = updateSelectedMenuItemUseCase.execute(
                    UpdateSelectedMenuItemUseCase.Params(
                        items = menuItems,
                        filterType = filterType,
                    )
                ),
            )
        } ?: getFirstSuccessStateUseCase.execute(
            GetFirstSuccessStateUseCase.Params(
                filterType = filterType,
                requestedMoreMovies = requestedMoreMovies,
            )
        )
    }

    private fun ImmutableList<MovieCardInfo>.addAllDistinctly(
        newMovies: List<MovieCardInfo>,
    ): List<MovieCardInfo> = (this + newMovies).distinctBy { it.movieId }
}
