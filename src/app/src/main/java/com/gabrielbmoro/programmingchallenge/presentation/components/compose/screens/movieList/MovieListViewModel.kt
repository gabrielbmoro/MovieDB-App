package com.gabrielbmoro.programmingchallenge.presentation.components.compose.screens.movieList

import android.app.Application
import androidx.lifecycle.*
import com.gabrielbmoro.programmingchallenge.presentation.components.PaginationController
import com.gabrielbmoro.programmingchallenge.repository.entities.Movie
import com.gabrielbmoro.programmingchallenge.repository.entities.MovieListType
import com.gabrielbmoro.programmingchallenge.repository.entities.Page
import com.gabrielbmoro.programmingchallenge.usecases.GetFavoriteMoviesUseCase
import com.gabrielbmoro.programmingchallenge.usecases.GetPopularMoviesUseCase
import com.gabrielbmoro.programmingchallenge.usecases.GetTopRatedMoviesUseCase
import com.google.accompanist.swiperefresh.SwipeRefreshState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class MovieListViewModel @Inject constructor(
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
            try {
                val favoriteMovies = getFavoriteMoviesUseCase.execute()
                moviesMutableStateFlow.emit(favoriteMovies)
            } catch (exception: Exception) {
                Timber.e(exception)
            } finally {
                loadingMutableStateFlow.emit(false)
            }
        }
    }

    private fun fetchPaginatedMovies(pageNumber: Int) {
        loadingMutableStateFlow.value = true
        viewModelScope.launch {
            val page : Page? = when (type) {
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

            val existingMovies = moviesMutableStateFlow.value ?: emptyList()
            val updatedList = existingMovies.toMutableList().apply {
                addAll(page?.movies ?: emptyList())
            }.toList()

            moviesMutableStateFlow.emit(updatedList)

            val hasNextPage = (page != null && page.pageNumber < page.totalPages)
            moviesPaginationController?.resultReceived(hasNextPage)

            loadingMutableStateFlow.emit(false)
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