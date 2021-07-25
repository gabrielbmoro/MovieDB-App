package com.gabrielbmoro.programmingchallenge.presentation

import android.app.Application
import androidx.compose.runtime.State
import androidx.lifecycle.*
import androidx.lifecycle.viewmodel.compose.viewModel
import com.gabrielbmoro.programmingchallenge.repository.entities.Movie
import com.gabrielbmoro.programmingchallenge.usecases.GetFavoriteMoviesUseCase
import com.gabrielbmoro.programmingchallenge.usecases.GetPopularMoviesUseCase
import com.gabrielbmoro.programmingchallenge.usecases.GetTopRatedMoviesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
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

    private val popularMoviesLiveData = MutableLiveData<List<Movie>?>()
    val popularMovies: LiveData<List<Movie>?> = popularMoviesLiveData

    private val favoriteMoviesLiveData = MutableLiveData<List<Movie>?>()
    val favoriteMovies: LiveData<List<Movie>?> = favoriteMoviesLiveData

    init {
        fetchTopRatedMovies()
        fetchPopularMovies()
        fetchFavoriteMovies()
    }

    private fun fetchTopRatedMovies() {
        viewModelScope.launch {
            val topRatedMovies = getTopRatedMoviesUseCase.execute(1).results
            topRatedMoviesLiveData.postValue(topRatedMovies)
        }
    }

    private fun fetchPopularMovies() {
        viewModelScope.launch {
            val popularMovies = getPopularMoviesUseCase.execute(1).results
            popularMoviesLiveData.postValue(popularMovies)
        }
    }

    private fun fetchFavoriteMovies() {
        viewModelScope.launch {
            val favoriteMovies = getFavoriteMoviesUseCase.execute()
            favoriteMoviesLiveData.postValue(favoriteMovies)
        }
    }
}