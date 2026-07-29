package com.gabrielbmoro.moviedb.feature.tvshows.ui.screens.tvshows

import com.gabrielbmoro.moviedb.desingsystem.error.ErrorInfo
import com.gabrielbmoro.moviedb.domain.model.HttpException
import com.gabrielbmoro.moviedb.domain.model.TvShow
import com.gabrielbmoro.moviedb.feature.tvshows.components.TvShowsHandler
import com.gabrielbmoro.moviedb.platform.logging.LoggerHelper
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

class TvShowsViewModel(
    ioDispatcher: CoroutineDispatcher,
    private val loggerHelper: LoggerHelper,
    private val tvShowsHandler: TvShowsHandler,
) : BaseViewModel<TvShowsState, TvShowsIntent, UiEvent>(ioDispatcher),
    PagingController by SimplePaging() {
    private var _paginationJob: Job? = null

    init {
        loggerHelper.plant(this::class)

        executeIntent(TvShowsIntent.Setup)
    }

    override fun executeIntent(intent: TvShowsIntent) {
        when (intent) {
            is TvShowsIntent.RequestMoreTvShows -> {
                loggerHelper.logDebug(
                    message = "${getSelectedFilterName()} - Request more tv shows...}",
                )
                requestNextPage()
            }

            TvShowsIntent.Setup -> handleSetup()

            is TvShowsIntent.SelectFilterMenuItem -> {
                launchIo {
                    updateState {
                        it.copy(
                            selectedFilterMenu = intent.menuItem.type,
                            tvShowCardInfos = persistentListOf(),
                            isLoading = true,
                            menuItems = uiState.value.menuItems.updateAccordingToFilterType(
                                newFilterType = intent.menuItem.type,
                            ).toPersistentList(),
                        )
                    }
                }

                handleSetup()
            }
        }
    }

    private fun handleSetup() {
        _paginationJob?.cancel()

        resetPaging()

        _paginationJob = launchIo {
            currentPage.collectLatest { pageIndex ->
                loggerHelper.logDebug(
                    message = "${getSelectedFilterName()} - " +
                        "Request received to fetch the page $pageIndex}",
                )

                val requestedMoreTvShows = onRequestMoreTvShows(pageIndex)
                val requestedTvShowCardInfos = requestedMoreTvShows.map(::toTvShowCardInfo)
                updateState {
                    it.copy(
                        tvShowCardInfos = uiState.value.tvShowCardInfos.addAllDistinctly(
                            requestedTvShowCardInfos,
                        ).toPersistentList(),
                        isLoading = false,
                        errorInfo = null,
                    )
                }
            }
        }
    }

    override fun defaultEmptyState(): TvShowsState {
        return TvShowsState(
            tvShowCardInfos = persistentListOf(),
            selectedFilterMenu = TvShowFilterType.Popular,
            menuItems = persistentListOf(
                FilterMenuItem(
                    selected = true,
                    type = TvShowFilterType.Popular,
                ),
                FilterMenuItem(
                    selected = false,
                    type = TvShowFilterType.TopRated,
                ),
                FilterMenuItem(
                    selected = false,
                    type = TvShowFilterType.OnTheAir,
                ),
                FilterMenuItem(
                    selected = false,
                    type = TvShowFilterType.AiringToday,
                ),
            ),
            isLoading = false,
            errorInfo = null,
        )
    }

    override fun onFailure(throwable: Throwable) {
        val errorInfo = if (throwable is HttpException) {
            ErrorInfo.SOMETHING_WRONG_HAPPENED
        } else {
            ErrorInfo.NETWORK_ERROR
        }
        launchIo {
            updateState {
                it.copy(
                    isLoading = false,
                    errorInfo = errorInfo,
                )
            }
        }
    }

    private fun getSelectedFilterName() = uiState.value.selectedFilterMenu.name

    private suspend fun onRequestMoreTvShows(pageIndex: Int): List<TvShow> =
        tvShowsHandler.getTvShowsFromFilter(
            filter = uiState.value.selectedFilterMenu,
            page = pageIndex,
        )

    private fun toTvShowCardInfo(tvShow: TvShow) = TvShowCardInfo(
        tvShowId = tvShow.id,
        tvShowTitle = tvShow.name,
        tvShowPosterUrl = tvShow.posterImageUrl.orEmpty(),
    )

    private fun ImmutableList<TvShowCardInfo>.addAllDistinctly(
        newTvShows: List<TvShowCardInfo>,
    ): ImmutableList<TvShowCardInfo> {
        return toMutableList().apply {
            addAll(newTvShows)
        }.distinctBy { it.tvShowId }
            .toPersistentList()
    }

    private fun List<FilterMenuItem>.updateAccordingToFilterType(
        newFilterType: TvShowFilterType,
    ): List<FilterMenuItem> {
        return map {
            it.copy(
                selected = it.type == newFilterType,
            )
        }
    }
}
