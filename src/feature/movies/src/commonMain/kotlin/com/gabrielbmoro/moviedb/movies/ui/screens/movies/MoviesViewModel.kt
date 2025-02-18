package com.gabrielbmoro.moviedb.movies.ui.screens.movies

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gabrielbmoro.moviedb.domain.entities.Movie
import com.gabrielbmoro.moviedb.domain.usecases.GetNowPlayingMoviesUseCase
import com.gabrielbmoro.moviedb.domain.usecases.GetPopularMoviesUseCase
import com.gabrielbmoro.moviedb.domain.usecases.GetTopRatedMoviesUseCase
import com.gabrielbmoro.moviedb.domain.usecases.GetUpcomingMoviesUseCase
import com.gabrielbmoro.moviedb.logging.LoggerHelper
import com.gabrielbmoro.moviedb.movies.domain.model.FilterMenuItem
import com.gabrielbmoro.moviedb.movies.domain.model.FilterType
import com.gabrielbmoro.moviedb.movies.domain.model.MoviesUseCases
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
    private val useCases: MoviesUseCases,
) : ViewModel(), ViewModelMvi<Intent>, PagingController by SimplePaging() {

    private val _uiState = MutableStateFlow(useCases.getDefaultEmptyState())
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

    private suspend fun onRequestMoreMovies(pageIndex: Int): List<Movie> = useCases.run {
        when (_uiState.value.selectedFilterMenu) {
            FilterType.NowPlaying -> {
                getNowPlayingMoviesUseCase.execute(
                    GetNowPlayingMoviesUseCase.Params(pageIndex)
                )
            }

            FilterType.TopRated -> {
                getTopRatedMoviesUseCase.execute(
                    GetTopRatedMoviesUseCase.Params(pageIndex)
                )
            }

            FilterType.Popular -> {
                getPopularMoviesUseCase.execute(
                    GetPopularMoviesUseCase.Params(pageIndex)
                )
            }

            FilterType.UpComing -> {
                getUpcomingMoviesUseCase.execute(
                    GetUpcomingMoviesUseCase.Params(
                        pageIndex
                    )
                )
            }
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
