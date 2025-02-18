package com.gabrielbmoro.moviedb.movies.ui.screens.movies

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gabrielbmoro.moviedb.domain.entities.Movie
import com.gabrielbmoro.moviedb.logging.LoggerHelper
import com.gabrielbmoro.moviedb.movies.domain.interactor.MoviesInteractor
import com.gabrielbmoro.moviedb.movies.domain.model.FilterMenuItem
import com.gabrielbmoro.moviedb.movies.domain.model.FilterType
import com.gabrielbmoro.moviedb.platform.ViewModelMvi
import com.gabrielbmoro.moviedb.platform.paging.PagingController
import com.gabrielbmoro.moviedb.platform.paging.SimplePaging
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toPersistentList
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class MoviesViewModel(
    private val ioDispatcher: CoroutineDispatcher,
    private val loggerHelper: LoggerHelper,
    private val interactor: MoviesInteractor,
) : ViewModel(), ViewModelMvi<Intent>, PagingController by SimplePaging() {

    private val _uiState = MutableStateFlow(interactor.getDefaultEmptyState())
    val uiState = _uiState.stateIn(viewModelScope, SharingStarted.Eagerly, _uiState.value)

    private var _paginationJob: Job? = null

    init {
        loggerHelper.plant(this::class)

        execute(Intent.Setup)
    }

    override fun execute(intent: Intent) {
        when (intent) {
            is Intent.RequestMoreMovies -> {
                loggerHelper.logDebug(
                    message = "${getSelectedFilterName()} - Request more movies...}"
                )
                requestNextPage()
            }

            Intent.Setup -> {
                _paginationJob?.cancel()

                resetPaging()

                _paginationJob = viewModelScope.launch(ioDispatcher) {
                    currentPage.collectLatest { pageIndex ->
                        loggerHelper.logDebug(
                            message = "${getSelectedFilterName()} - " +
                                    "Request received to fetch the page $pageIndex}"
                        )

                        runCatching {
                            onRequestMoreMovies(pageIndex)
                        }.getOrNull()?.let { requestedMoreMovies ->
                            val requestedMoviesCardInfo = requestedMoreMovies.map(
                                ::toMovieCardInfo
                            )
                            _uiState.update {
                                it.copy(
                                    movieCardInfos = it.movieCardInfos.addAllDistinctly(
                                        requestedMoviesCardInfo
                                    ).toPersistentList(),
                                    isLoading = false,
                                )
                            }
                        }
                    }
                }
            }

            is Intent.SelectFilterMenuItem -> {
                _uiState.update {
                    it.copy(
                        selectedFilterMenu = intent.menuItem.type,
                        movieCardInfos = persistentListOf(),
                        isLoading = true,
                        menuItems = it.menuItems.updateAccordingToFilterType(
                            newFilterType = intent.menuItem.type
                        )
                    )
                }

                execute(Intent.Setup)
            }
        }
    }

    private fun getSelectedFilterName() = _uiState.value.selectedFilterMenu.name

    private suspend fun onRequestMoreMovies(pageIndex: Int): List<Movie> = interactor.run {
        when (_uiState.value.selectedFilterMenu) {
            FilterType.NowPlaying -> getNowPlayingMovies(pageIndex)

            FilterType.TopRated -> getTopRatedMovies(pageIndex)

            FilterType.Popular -> getPopularMovies(pageIndex)

            FilterType.UpComing -> getUpcomingMovies(pageIndex)
        }
    }

    private fun toMovieCardInfo(movie: Movie) = MovieCardInfo(
        movieId = movie.id,
        movieTitle = movie.title,
        moviePosterUrl = movie.posterImageUrl.orEmpty(),
    )

    private fun ImmutableList<MovieCardInfo>.addAllDistinctly(
        newMovies: List<MovieCardInfo>
    ): ImmutableList<MovieCardInfo> {
        return toMutableList().apply {
            addAll(newMovies)
        }.distinctBy { it.movieId }
            .toPersistentList()
    }

    private fun List<FilterMenuItem>.updateAccordingToFilterType(newFilterType: FilterType): List<FilterMenuItem> {
        return map {
            it.copy(
                selected = it.type == newFilterType
            )
        }
    }
}
