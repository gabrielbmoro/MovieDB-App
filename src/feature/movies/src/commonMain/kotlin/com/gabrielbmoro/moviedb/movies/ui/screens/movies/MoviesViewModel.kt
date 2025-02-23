package com.gabrielbmoro.moviedb.movies.ui.screens.movies

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gabrielbmoro.moviedb.movies.domain.interactor.MoviesInteractor
import com.gabrielbmoro.moviedb.movies.domain.model.MoviesState
import com.gabrielbmoro.moviedb.platform.IntentExecutor
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class MoviesViewModel(
    val uiState: StateFlow<MoviesState>,
    private val ioDispatcher: CoroutineDispatcher,
    private val interactor: MoviesInteractor,
) : ViewModel(), IntentExecutor<MoviesIntent> {

    init {
        viewModelScope.launch(ioDispatcher) {
            interactor.listenToPagination()
        }
    }

    override fun execute(intent: MoviesIntent) {
        interactor.run {
            when (intent) {
                is MoviesIntent.OnEndScroll -> onEndScroll()
                is MoviesIntent.SelectFilterMenuItem -> viewModelScope.launch(ioDispatcher) {
                    onSelectFilter(intent.filterType)
                }
            }
        }
    }
}
