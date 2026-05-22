package com.gabrielbmoro.moviedb.search.ui.screens.search

import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.viewModelScope
import com.gabrielbmoro.moviedb.domain.MoviesRepository
import com.gabrielbmoro.moviedb.domain.model.Movie
import com.gabrielbmoro.moviedb.platform.viewmodel.BaseViewModel
import com.gabrielbmoro.moviedb.platform.viewmodel.UiEvent
import com.gabrielbmoro.moviedb.search.ui.widgets.MovieCardInfo
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.launch

@OptIn(FlowPreview::class)
class SearchViewModel(
    private val query: String?,
    private val repository: MoviesRepository,
    ioCoroutinesDispatcher: CoroutineDispatcher,
) : BaseViewModel<SearchUIState, SearchUserIntent, UiEvent>(
    ioCoroutinesDispatcher,
) {
    private val searchFlow = MutableSharedFlow<String>()

    override fun defaultEmptyState() = SearchUIState(TextFieldValue(""))

    init {
        query?.let {
            executeIntent(SearchUserIntent.SearchBy(TextFieldValue(query)))
        }

        viewModelScope.launch(ioCoroutinesDispatcher) {
            searchFlow.debounce(SEARCH_DEBOUNCE_DELAY_IN_MS).collect { searchQuery ->
                val result = repository.searchMovieBy(
                    query = searchQuery,
                )

                val movieCardsInfos = result.map(::mapToMovieCardInfo)?.toImmutableList()

                updateState {
                    it.copy(
                        results = movieCardsInfos,
                    )
                }
            }
        }
    }

    override fun executeIntent(intent: SearchUserIntent) {
        when (intent) {
            is SearchUserIntent.SearchBy -> {
                launchIo {
                    val searchQuery = intent.query
                    updateState {
                        it.copy(
                            searchQuery = searchQuery,
                        )
                    }

                    searchFlow.emit(searchQuery.text)
                }
            }

            is SearchUserIntent.ClearSearchField -> {
                launchIo {
                    updateState {
                        it.copy(
                            searchQuery = TextFieldValue(""),
                            results = persistentListOf(),
                        )
                    }
                }
            }
        }
    }

    override fun onFailure(throwable: Throwable) = Unit

    private fun mapToMovieCardInfo(movie: Movie): MovieCardInfo {
        return MovieCardInfo(
            movieId = movie.id,
            movieTitle = movie.title,
            moviePosterImageUrl = movie.posterImageUrl.orEmpty(),
            movieOverview = movie.overview,
            movieVotesAverage = movie.votesAverage,
        )
    }

    companion object {
        private const val SEARCH_DEBOUNCE_DELAY_IN_MS = 600L
    }
}
