package com.gabrielbmoro.programmingchallenge.ui.screens.home

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.*
import com.gabrielbmoro.programmingchallenge.domain.model.DataOrException
import com.gabrielbmoro.programmingchallenge.domain.model.Movie
import com.gabrielbmoro.programmingchallenge.ui.common.PaginationController
import com.gabrielbmoro.programmingchallenge.domain.model.MovieListType
import com.gabrielbmoro.programmingchallenge.domain.model.Page
import com.gabrielbmoro.programmingchallenge.domain.usecases.GetFavoriteMoviesUseCase
import com.gabrielbmoro.programmingchallenge.domain.usecases.GetPopularMoviesUseCase
import com.gabrielbmoro.programmingchallenge.domain.usecases.GetTopRatedMoviesUseCase
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
        HomeUIState(selectedMovieListType = MovieListType.TOP_RATED)
    )
    val uiState: State<HomeUIState> = _uiState

    private var moviesPaginationController: PaginationController? = null

    suspend fun setup(movieListType: MovieListType) {
        this._uiState.value = _uiState.value.copy(
            selectedMovieListType = movieListType
        )

        when (movieListType) {
            MovieListType.FAVORITE -> {
                runServerCall(
                    serverCall = {
                        val result = fetchFavoriteMovies()
                        onResult(result)
                    }
                )
            }
            else -> {
                moviesPaginationController = PaginationController.build { pageNumber ->
                    runServerCall(
                        serverCall = {
                            val result = fetchPaginatedMovies(pageNumber)
                            onResult(result ?: emptyList())
                        }
                    )
                }
                moviesPaginationController?.requestMore()
            }
        }
    }

    private fun runServerCall(serverCall: suspend (() -> Unit)) {
        viewModelScope.launch {
            onLoading()
            serverCall()
        }.invokeOnCompletion {
            onLoaded()
        }
    }

    private suspend fun fetchFavoriteMovies(): List<Movie> {
        return getFavoriteMoviesUseCase().data ?: emptyList()
    }

    private suspend fun fetchPaginatedMovies(pageNumber: Int): List<Movie>? {
        val page: DataOrException<Page, Exception>? =
            when (_uiState.value.selectedMovieListType) {
                MovieListType.TOP_RATED -> {
                    getTopRatedMoviesUseCase(pageNumber)
                }
                MovieListType.POPULAR -> {
                    getPopularMoviesUseCase(pageNumber)
                }
                else -> {
                    null
                }
            }

        return if (page?.data != null) {
            val existingMovies = _uiState.value.movies ?: emptyList()
            val updatedList = existingMovies.toMutableList().apply {
                addAll(page.data.movies)
            }.toList()

            val hasNextPage = (page.data.pageNumber < page.data.totalPages)
            moviesPaginationController?.resultReceived(hasNextPage)
            updatedList
        } else {
            null
        }
    }

    private fun onLoading() {
        _uiState.value = _uiState.value.copy(
            isLoading = true
        )
    }

    private fun onLoaded() {
        _uiState.value = _uiState.value.copy(
            isLoading = false
        )
    }

    private fun onResult(movies: List<Movie>) {
        this._uiState.value = this._uiState.value.copy(
            movies = movies
        )
    }

    suspend fun refresh() {
        moviesPaginationController = null
        _uiState.value = _uiState.value.copy(
            movies = null,
        )
        setup(movieListType = _uiState.value.selectedMovieListType)
    }

    fun requestMore() {
        moviesPaginationController?.requestMore()
    }
}