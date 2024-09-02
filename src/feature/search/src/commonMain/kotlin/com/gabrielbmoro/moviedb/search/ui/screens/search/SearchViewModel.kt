package com.gabrielbmoro.moviedb.search.ui.screens.search

import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gabrielbmoro.moviedb.domain.entities.Movie
import com.gabrielbmoro.moviedb.domain.usecases.SearchMovieUseCase
import com.gabrielbmoro.moviedb.platform.ViewModelMvi
import com.gabrielbmoro.moviedb.search.ui.widgets.MovieCardInfo
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class SearchViewModel(
    private val searchMovieUseCase: SearchMovieUseCase,
    private val ioCoroutinesDispatcher: CoroutineDispatcher,
) : ViewModel(), ViewModelMvi<SearchUserIntent> {

    private val _uiState = MutableStateFlow(this.defaultEmptyState())
    val uiState = _uiState.stateIn(viewModelScope, SharingStarted.Eagerly, _uiState.value)

    private fun defaultEmptyState() = SearchUIState(TextFieldValue(""))

    override fun execute(intent: SearchUserIntent) {
        when (intent) {
            is SearchUserIntent.SearchBy -> {
                viewModelScope.launch(ioCoroutinesDispatcher) {
                    val result = searchMovieUseCase.execute(
                        SearchMovieUseCase.Params(
                            query = intent.query.text
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

            is SearchUserIntent.ClearSearchField -> {
                _uiState.update {
                    it.copy(
                        searchQuery = TextFieldValue("")
                    )
                }
                execute(SearchUserIntent.SearchBy(TextFieldValue("")))
            }

            is SearchUserIntent.SearchInputFieldChanged -> {
                _uiState.update {
                    it.copy(
                        searchQuery = intent.query
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
}
