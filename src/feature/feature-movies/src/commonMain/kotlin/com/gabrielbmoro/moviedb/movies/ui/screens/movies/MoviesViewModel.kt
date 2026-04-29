package com.gabrielbmoro.moviedb.movies.ui.screens.movies

import MoviesHandler
import com.gabrielbmoro.moviedb.domain.entities.Movie
import com.gabrielbmoro.moviedb.logging.LoggerHelper
import com.gabrielbmoro.moviedb.platform.paging.PagingController
import com.gabrielbmoro.moviedb.platform.paging.SimplePaging
import com.gabrielbmoro.moviedb.platform.viewmodel.BaseViewModel
import com.gabrielbmoro.moviedb.platform.viewmodel.UiEvent
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toPersistentList
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collectLatest

class MoviesViewModel(
    ioDispatcher: CoroutineDispatcher,
    private val loggerHelper: LoggerHelper,
    private val moviesHandler: MoviesHandler,
) : BaseViewModel<MoviesState, MoviesIntent, UiEvent>(ioDispatcher),
    PagingController by SimplePaging() {
    private var _paginationJob: Job? = null

    init {
        loggerHelper.plant(this::class)

        executeIntent(MoviesIntent.Setup)
    }

    override fun executeIntent(intent: MoviesIntent) {
        when (intent) {
            is MoviesIntent.RequestMoreMovies -> {
                loggerHelper.logDebug(
                    message = "${getSelectedFilterName()} - Request more movies...}",
                )
                requestNextPage()
            }

            MoviesIntent.Setup -> {
                _paginationJob?.cancel()

                resetPaging()

                _paginationJob = launchIo {
                    currentPage.collectLatest { pageIndex ->
                        loggerHelper.logDebug(
                            message = "${getSelectedFilterName()} - " +
                                    "Request received to fetch the page $pageIndex}",
                        )

                        val requestedMoreMovies = onRequestMoreMovies(pageIndex)
                        val requestedMoviesCardInfo = requestedMoreMovies.map(
                            ::toMovieCardInfo,
                        )
                        updateState {
                            it.copy(
                                movieCardInfos = uiState.value.movieCardInfos.addAllDistinctly(
                                    requestedMoviesCardInfo,
                                ).toPersistentList(),
                                isLoading = false,
                            )
                        }
                    }
                }
            }

            is MoviesIntent.SelectFilterMenuItem -> {
                launchIo {
                    updateState {
                        it.copy(
                            selectedFilterMenu = intent.menuItem.type,
                            movieCardInfos = persistentListOf(),
                            isLoading = true,
                            menuItems = uiState.value.menuItems.updateAccordingToFilterType(
                                newFilterType = intent.menuItem.type,
                            ).toPersistentList(),
                        )
                    }
                }

                executeIntent(MoviesIntent.Setup)
            }
        }
    }

    override fun defaultEmptyState(): MoviesState {
        return MoviesState(
            movieCardInfos = persistentListOf(),
            selectedFilterMenu = FilterType.NowPlaying,
            menuItems = persistentListOf(
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
            ),
            isLoading = false,
            showError = false,
        )
    }

    override fun onFailure(throwable: Throwable) {
        launchIo {
            updateState {
                it.copy(
                    isLoading = false,
                    showError = true
                )
            }
        }
    }

    private fun getSelectedFilterName() = uiState.value.selectedFilterMenu.name

    private suspend fun onRequestMoreMovies(pageIndex: Int): List<Movie> =
        moviesHandler.getMoviesFromFilter(
            filter = uiState.value.selectedFilterMenu,
            page = pageIndex,
        )

    private fun toMovieCardInfo(movie: Movie) = MovieCardInfo(
        movieId = movie.id,
        movieTitle = movie.title,
        moviePosterUrl = movie.posterImageUrl.orEmpty(),
    )

    private fun ImmutableList<MovieCardInfo>.addAllDistinctly(
        newMovies: List<MovieCardInfo>,
    ): ImmutableList<MovieCardInfo> {
        return toMutableList().apply {
            addAll(newMovies)
        }.distinctBy { it.movieId }
            .toPersistentList()
    }

    private fun List<FilterMenuItem>.updateAccordingToFilterType(newFilterType: FilterType): List<FilterMenuItem> {
        return map {
            it.copy(
                selected = it.type == newFilterType,
            )
        }
    }
}
