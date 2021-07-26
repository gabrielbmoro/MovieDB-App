package com.gabrielbmoro.programmingchallenge.presentation

import android.app.Application
import androidx.lifecycle.*
import com.gabrielbmoro.programmingchallenge.presentation.components.PaginationController
import com.gabrielbmoro.programmingchallenge.repository.entities.Movie
import com.gabrielbmoro.programmingchallenge.usecases.GetFavoriteMoviesUseCase
import com.gabrielbmoro.programmingchallenge.usecases.GetPopularMoviesUseCase
import com.gabrielbmoro.programmingchallenge.usecases.GetTopRatedMoviesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    application: Application,
    private val getFavoriteMoviesUseCase: GetFavoriteMoviesUseCase,
    private val getTopRatedMoviesUseCase: GetTopRatedMoviesUseCase,
    private val getPopularMoviesUseCase: GetPopularMoviesUseCase
) : AndroidViewModel(application) {

    private val topRatedMoviesLiveData = MutableLiveData<List<Movie>?>()
    val topRatedMovies: LiveData<List<Movie>?> = topRatedMoviesLiveData
    private val topRatedMoviesPaginationController = PaginationController.build { pageNumber ->
        fetchTopRatedMovies(pageNumber)
    }

    private val popularMoviesLiveData = MutableLiveData<List<Movie>?>()
    val popularMovies: LiveData<List<Movie>?> = popularMoviesLiveData
    private val popularMoviesPaginationController = PaginationController.build { pageNumber ->
        fetchPopularMovies(pageNumber)
    }

    private val favoriteMoviesLiveData = MutableLiveData<List<Movie>?>()
    val favoriteMovies: LiveData<List<Movie>?> = favoriteMoviesLiveData

    private val loadingLiveData = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> = loadingLiveData

    init {
        topRatedMoviesPaginationController.requestMore()
        popularMoviesPaginationController.requestMore()
        fetchFavoriteMovies()
    }

    private fun fetchTopRatedMovies(pageNumber: Int) {
        loadingLiveData.value = true
        viewModelScope.launch {
            val newMovies = getTopRatedMoviesUseCase.execute(pageNumber).results ?: emptyList()
            val existingMovies = topRatedMoviesLiveData.value ?: emptyList()
            val updatedList = existingMovies
                .toMutableList()
                .apply { addAll(newMovies) }
                .toList()
            topRatedMoviesLiveData.postValue(updatedList)
            topRatedMoviesPaginationController.resultReceived()
            loadingLiveData.postValue(false)
        }
    }

    private fun fetchPopularMovies(pageNumber: Int) {
        loadingLiveData.value = true
        viewModelScope.launch {
            val newMovies = getPopularMoviesUseCase.execute(pageNumber).results ?: emptyList()
            val existingMovies = popularMoviesLiveData.value ?: emptyList()
            val updatedList = existingMovies
                .toMutableList()
                .apply { addAll(newMovies) }
                .toList()
            popularMoviesLiveData.postValue(updatedList)
            popularMoviesPaginationController.resultReceived()
            loadingLiveData.postValue(false)
        }
    }

    private fun fetchFavoriteMovies() {
        loadingLiveData.value = true
        viewModelScope.launch {
            val favoriteMovies = getFavoriteMoviesUseCase.execute()
            favoriteMoviesLiveData.postValue(favoriteMovies)
            loadingLiveData.postValue(false)
        }
    }

    fun refreshFavoriteMovies() {
        fetchFavoriteMovies()
    }

    fun requestMoreTopRatedMoviesCallback() {
        topRatedMoviesPaginationController.requestMore()
    }

    fun requestMorePopularMoviesCallback() {
        popularMoviesPaginationController.requestMore()
    }

    fun requestMoreFavoriteMoviesCallback() {
        Timber.d("Pagination is not impleted to favorite movies")
    }
}