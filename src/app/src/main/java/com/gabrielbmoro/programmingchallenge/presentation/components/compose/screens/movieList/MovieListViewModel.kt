package com.gabrielbmoro.programmingchallenge.presentation.components.compose.screens.movieList

import android.app.Application
import androidx.lifecycle.*
import com.gabrielbmoro.programmingchallenge.presentation.components.PaginationController
import com.gabrielbmoro.programmingchallenge.repository.entities.Movie
import com.gabrielbmoro.programmingchallenge.repository.entities.MovieListType
import com.gabrielbmoro.programmingchallenge.usecases.GetFavoriteMoviesUseCase
import com.gabrielbmoro.programmingchallenge.usecases.GetPopularMoviesUseCase
import com.gabrielbmoro.programmingchallenge.usecases.GetTopRatedMoviesUseCase
import com.google.accompanist.swiperefresh.SwipeRefreshState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieListViewModel @Inject constructor(
    application: Application,
    private val getFavoriteMoviesUseCase: GetFavoriteMoviesUseCase,
    private val getTopRatedMoviesUseCase: GetTopRatedMoviesUseCase,
    private val getPopularMoviesUseCase: GetPopularMoviesUseCase
) : AndroidViewModel(application) {

    private val moviesLiveData = MutableLiveData<List<Movie>?>()
    val movies: LiveData<List<Movie>?> = moviesLiveData

    private var moviesPaginationController : PaginationController? = null

    private val loadingLiveData = MutableLiveData(false)
    val loading: LiveData<Boolean> = loadingLiveData

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
            fetchPaginatedMovies(pageNumber = 1)
        }
    }

    private fun fetchFavoriteMovies() {
        loadingLiveData.value = true
        viewModelScope.launch {
            val favoriteMovies = getFavoriteMoviesUseCase.execute()
            moviesLiveData.postValue(favoriteMovies)
            loadingLiveData.postValue(false)
        }
    }

    private fun fetchPaginatedMovies(pageNumber: Int) {
        loadingLiveData.value = true
        viewModelScope.launch {
            val newMovies: List<Movie> = when (type) {
                is MovieListType.TopRated -> {
                    getTopRatedMoviesUseCase.execute(pageNumber).movies
                }
                is MovieListType.Popular -> {
                    getPopularMoviesUseCase.execute(pageNumber).movies
                }
                else -> {
                    emptyList()
                }
            }

            val existingMovies = moviesLiveData.value ?: emptyList()
            val updatedList = existingMovies
                .toMutableList()
                .apply { addAll(newMovies) }
                .toList()

            moviesLiveData.postValue(updatedList)
            moviesPaginationController?.resultReceived()
            loadingLiveData.postValue(false)
        }
    }

    fun refresh() {
        moviesLiveData.value = null
        moviesPaginationController?.reset()
        fetchMovies()
    }

    fun requestMore() {
        moviesPaginationController?.requestMore()
    }
}