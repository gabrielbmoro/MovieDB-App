package com.gabrielbmoro.programmingchallenge.ui.screens.home

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.*
import com.gabrielbmoro.programmingchallenge.domain.model.DataOrException
import com.gabrielbmoro.programmingchallenge.ui.common.PaginationController
import com.gabrielbmoro.programmingchallenge.domain.model.MovieListType
import com.gabrielbmoro.programmingchallenge.domain.model.Page
import com.gabrielbmoro.programmingchallenge.domain.usecases.GetFavoriteMoviesUseCase
import com.gabrielbmoro.programmingchallenge.domain.usecases.GetPopularMoviesUseCase
import com.gabrielbmoro.programmingchallenge.domain.usecases.GetTopRatedMoviesUseCase
import com.google.accompanist.swiperefresh.SwipeRefreshState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getFavoriteMoviesUseCase: GetFavoriteMoviesUseCase,
    private val getTopRatedMoviesUseCase: GetTopRatedMoviesUseCase,
    private val getPopularMoviesUseCase: GetPopularMoviesUseCase
) : ViewModel() {

    private val _uiState: MutableState<HomeUIState> = mutableStateOf(
        HomeUIState(selectedMovieListType = MovieListType.TopRated)
    )
    val uiState: State<HomeUIState> = _uiState

    private var moviesPaginationController: PaginationController? = null

    val swipeRefreshLiveData = SwipeRefreshState(false)

    fun setup(movieListType: MovieListType) {
        this._uiState.value = _uiState.value.copy(
            selectedMovieListType = movieListType,
            movies = null,
            isLoading = false
        )

        this.moviesPaginationController = PaginationController.build { pageNumber ->
            fetchPaginatedMovies(pageNumber)
        }

        fetchMovies()
    }

    private fun fetchMovies() {
        if (_uiState.value.selectedMovieListType is MovieListType.Favorite) {
            fetchFavoriteMovies()
        } else {
            moviesPaginationController?.requestMore()
        }
    }

    private fun fetchFavoriteMovies() {
        _uiState.value = _uiState.value.copy(
            isLoading = true
        )

        viewModelScope.launch {
            val favoriteMovies = getFavoriteMoviesUseCase.execute()
            if (favoriteMovies.data != null) {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    movies = favoriteMovies.data
                )
            }
            _uiState.value = _uiState.value.copy(
                isLoading = false
            )
        }
    }

    private fun fetchPaginatedMovies(pageNumber: Int) {
        _uiState.value = _uiState.value.copy(
            isLoading = true
        )
        viewModelScope.launch {
            val page: DataOrException<Page, Exception>? =
                when (_uiState.value.selectedMovieListType) {
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
                val existingMovies = _uiState.value.movies ?: emptyList()
                val updatedList = existingMovies.toMutableList().apply {
                    addAll(page.data.movies)
                }.toList()

                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    movies = updatedList
                )

                val hasNextPage = (page.data.pageNumber < page.data.totalPages)
                moviesPaginationController?.resultReceived(hasNextPage)
            }
        }
    }

    fun refresh() {
        _uiState.value = _uiState.value.copy(
            movies = null,
        )
        moviesPaginationController?.reset()
        fetchMovies()
    }

    fun requestMore() {
        moviesPaginationController?.requestMore()
    }
}