package com.gabrielbmoro.programmingchallenge.ui.screens.home

import androidx.lifecycle.*
import com.gabrielbmoro.programmingchallenge.domain.model.DataOrException
import com.gabrielbmoro.programmingchallenge.domain.model.Movie
import com.gabrielbmoro.programmingchallenge.ui.common.PaginationController
import com.gabrielbmoro.programmingchallenge.domain.model.MovieListType
import com.gabrielbmoro.programmingchallenge.domain.model.Page
import com.gabrielbmoro.programmingchallenge.domain.usecases.GetFavoriteMoviesUseCase
import com.gabrielbmoro.programmingchallenge.domain.usecases.GetPopularMoviesUseCase
import com.gabrielbmoro.programmingchallenge.domain.usecases.GetTopRatedMoviesUseCase
import com.gabrielbmoro.programmingchallenge.ui.common.widgets.SearchType
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class HomeViewModel(
    private val getFavoriteMoviesUseCase: GetFavoriteMoviesUseCase,
    private val getTopRatedMoviesUseCase: GetTopRatedMoviesUseCase,
    private val getPopularMoviesUseCase: GetPopularMoviesUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(
        HomeUIState(
            selectedMovieListType = MovieListType.TOP_RATED,
        )
    )
    val uiState = _uiState.stateIn(
        viewModelScope,
        SharingStarted.Eagerly,
        _uiState.value
    )

    private var moviesPaginationController: PaginationController? = null

    suspend fun setup(movieListType: MovieListType) {
        this._uiState.update {
            it.copy(
                selectedMovieListType = movieListType
            )
        }

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
            loaded()
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
        _uiState.update {
            it.copy(
                isLoading = true
            )
        }
    }

    private fun loaded() {
        _uiState.update {
            it.copy(
                isLoading = false
            )
        }
    }

    private fun onResult(movies: List<Movie>) {
        this._uiState.update {
            it.copy(
                movies = movies
            )
        }
    }

    fun requestMore() {
        moviesPaginationController?.requestMore()
    }

    fun onSearchBy(searchType: SearchType) {
        _uiState.update {
            it.copy(
                isLoading = true,
                movies = null
            )
        }
        moviesPaginationController = null

        viewModelScope.launch {
            setup(
                movieListType = when (searchType) {
                    SearchType.TOP_RATED -> MovieListType.TOP_RATED
                    SearchType.POPULAR -> MovieListType.POPULAR
                }
            )
        }
    }

    fun currentSearchType() = when (_uiState.value.selectedMovieListType) {
        MovieListType.TOP_RATED -> SearchType.TOP_RATED
        MovieListType.POPULAR -> SearchType.POPULAR
        else -> null
    }
}