package com.gabrielbmoro.programmingchallenge.ui.screens.home

import android.app.Application
import androidx.lifecycle.*
import com.gabrielbmoro.programmingchallenge.domain.model.DataOrException
import com.gabrielbmoro.programmingchallenge.ui.common.PaginationController
import com.gabrielbmoro.programmingchallenge.domain.model.Movie
import com.gabrielbmoro.programmingchallenge.domain.model.MovieListType
import com.gabrielbmoro.programmingchallenge.domain.model.Page
import com.gabrielbmoro.programmingchallenge.domain.usecases.GetFavoriteMoviesUseCase
import com.gabrielbmoro.programmingchallenge.domain.usecases.GetPopularMoviesUseCase
import com.gabrielbmoro.programmingchallenge.domain.usecases.GetTopRatedMoviesUseCase
import com.google.accompanist.swiperefresh.SwipeRefreshState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    application: Application,
    private val getFavoriteMoviesUseCase: GetFavoriteMoviesUseCase,
    private val getTopRatedMoviesUseCase: GetTopRatedMoviesUseCase,
    private val getPopularMoviesUseCase: GetPopularMoviesUseCase
) : AndroidViewModel(application) {

    private val moviesMutableStateFlow = MutableStateFlow<List<Movie>?>(null)
    val movies: StateFlow<List<Movie>?> = moviesMutableStateFlow

    private var moviesPaginationController: PaginationController? = null

    private val loadingMutableStateFlow = MutableStateFlow(false)
    val loading: StateFlow<Boolean> = loadingMutableStateFlow

    val swipeRefreshLiveData = SwipeRefreshState(false)

    private var type: MovieListType? = null

    fun setup(movieListType: MovieListType) {
        this.type = movieListType

        this.moviesPaginationController = PaginationController.build { pageNumber ->
            fetchPaginatedMovies(pageNumber)
        }

        fetchMovies()
    }

    private fun fetchMovies() {
        if (type is MovieListType.Favorite) {
            fetchFavoriteMovies()
        } else {
            moviesPaginationController?.requestMore()
        }
    }

    private fun fetchFavoriteMovies() {
        loadingMutableStateFlow.value = true
        viewModelScope.launch {
            val favoriteMovies = getFavoriteMoviesUseCase.execute()
            if (favoriteMovies.data != null) {
                moviesMutableStateFlow.emit(favoriteMovies.data)
            }
            loadingMutableStateFlow.emit(false)
        }
    }

    private fun fetchPaginatedMovies(pageNumber: Int) {
        loadingMutableStateFlow.value = true
        viewModelScope.launch {
            val page: DataOrException<Page, Exception>? = when (type) {
                is MovieListType.TopRated -> {
                    getTopRatedMoviesUseCase.execute(pageNumber)
                }
                is MovieListType.Popular -> {
                    getPopularMoviesUseCase.execute(pageNumber)
                }
                else -> {
                    null
                }
            }

            if (page?.data != null) {
                val existingMovies = moviesMutableStateFlow.value ?: emptyList()
                val updatedList = existingMovies.toMutableList().apply {
                    addAll(page.data.movies)
                }.toList()

                moviesMutableStateFlow.emit(updatedList)

                val hasNextPage = (page.data.pageNumber < page.data.totalPages)
                moviesPaginationController?.resultReceived(hasNextPage)

                loadingMutableStateFlow.emit(false)
            }
        }
    }

    fun refresh() {
        moviesMutableStateFlow.value = null
        moviesPaginationController?.reset()
        fetchMovies()
    }

    fun requestMore() {
        moviesPaginationController?.requestMore()
    }
}