package com.gabrielbmoro.moviedb.search.ui.screens.search

import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gabrielbmoro.moviedb.domain.entities.Movie
import com.gabrielbmoro.moviedb.domain.usecases.SearchMovieUseCase
import com.gabrielbmoro.moviedb.platform.ViewModelMvi
import com.gabrielbmoro.moviedb.search.ui.widgets.MovieCardInfo
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@OptIn(FlowPreview::class)
class SearchViewModel(
    private val query: String?,
    private val searchMovieUseCase: SearchMovieUseCase,
    private val ioCoroutinesDispatcher: CoroutineDispatcher,
) : ViewModel(), ViewModelMvi<SearchUserIntent> {

    private val _uiState = MutableStateFlow(this.defaultEmptyState())
    val uiState = _uiState.stateIn(viewModelScope, SharingStarted.Eagerly, _uiState.value)

    private val searchFlow = MutableSharedFlow<String>()

    private fun defaultEmptyState() = SearchUIState(TextFieldValue(""))

    init {
        query?.let {
            execute(SearchUserIntent.SearchBy(TextFieldValue(query)))
        }

        viewModelScope.launch(ioCoroutinesDispatcher) {
            searchFlow.debounce(SEARCH_DEBOUNCE_DELAY_IN_MS).collect { searchQuery ->
                val result = searchMovieUseCase.execute(
                    SearchMovieUseCase.Params(
                        query = searchQuery
                    )
                )

                val movieCardsInfos = result.map(::mapToMovieCardInfo).toImmutableList()

                _uiState.update {
                    it.copy(
                        results = movieCardsInfos
                    )
                }
            }
        }
    }

    override fun execute(intent: SearchUserIntent) {
        when (intent) {
            is SearchUserIntent.SearchBy -> {
                val searchQuery = intent.query
                _uiState.update {
                    it.copy(
                        searchQuery = searchQuery
                    )
                }

                viewModelScope.launch(ioCoroutinesDispatcher) {
                    searchFlow.emit(searchQuery.text)
                }
            }

            is SearchUserIntent.ClearSearchField -> {
                _uiState.update {
                    it.copy(
                        searchQuery = TextFieldValue(""),
                        results = persistentListOf()
                    )
                }
            }
        }
    }

    private fun mapToMovieCardInfo(movie: Movie): MovieCardInfo {
        return MovieCardInfo(
            movieId = movie.id,
            movieTitle = movie.title,
            moviePosterImageUrl = movie.posterImageUrl ?: "",
            movieOverview = movie.overview,
            movieVotesAverage = movie.votesAverage,
        )
    }

    companion object {
        private const val SEARCH_DEBOUNCE_DELAY_IN_MS = 600L
    }
}
