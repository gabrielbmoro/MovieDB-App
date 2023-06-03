package com.gabrielbmoro.programmingchallenge.ui.screens.home

import androidx.lifecycle.*
import androidx.paging.PagingData
import com.gabrielbmoro.programmingchallenge.domain.model.Movie
import com.gabrielbmoro.programmingchallenge.domain.model.MovieListType
import com.gabrielbmoro.programmingchallenge.domain.usecases.GetFavoriteMoviesUseCase
import com.gabrielbmoro.programmingchallenge.domain.usecases.GetPopularMoviesUseCase
import com.gabrielbmoro.programmingchallenge.domain.usecases.GetTopRatedMoviesUseCase
import com.gabrielbmoro.programmingchallenge.ui.common.widgets.SearchType
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class HomeViewModel(
    private var movieListType: MovieListType,
    private val getFavoriteMoviesUseCase: GetFavoriteMoviesUseCase,
    private val getTopRatedMoviesUseCase: GetTopRatedMoviesUseCase,
    private val getPopularMoviesUseCase: GetPopularMoviesUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow<HomeUIState>(
        HomeUIState.MoviesTabUIState(
            selectedMovieType = MovieListType.TOP_RATED
        )
    )
    val uiState = _uiState.stateIn(
        viewModelScope,
        SharingStarted.Eagerly,
        _uiState.value
    )

    init {
        if (movieListType == MovieListType.FAVORITE) {
            loadFavoriteMovies()
        }
    }

    private fun loadFavoriteMovies() {
        viewModelScope.launch {
            _uiState.update {
                HomeUIState.FavoriteTabUIState(
                    isLoading = true,
                    favoriteMovies = null
                )
            }

            _uiState.update {
                HomeUIState.FavoriteTabUIState(
                    isLoading = false,
                    favoriteMovies = getFavoriteMoviesUseCase().data
                )
            }
        }.invokeOnCompletion {
            _uiState.update {
                if (it is HomeUIState.FavoriteTabUIState) {
                    it.copy(isLoading = false)
                } else it
            }
        }
    }

    fun getPaginatedMovies(): Flow<PagingData<Movie>>? {
        return when (movieListType) {
            MovieListType.POPULAR -> getPopularMoviesUseCase()
            MovieListType.TOP_RATED -> getTopRatedMoviesUseCase()
            else -> null
        }
    }


    fun onSearchBy(searchType: SearchType) {
        _uiState.update {
            if (it is HomeUIState.MoviesTabUIState) {
                this.movieListType = when (searchType) {
                    SearchType.TOP_RATED -> MovieListType.TOP_RATED
                    SearchType.POPULAR -> MovieListType.POPULAR
                }
                it.copy(
                    selectedMovieType = this.movieListType
                )
            } else it
        }
    }

    fun currentSearchType(): SearchType? {
        return if (_uiState.value is HomeUIState.MoviesTabUIState) {
            when ((_uiState.value as HomeUIState.MoviesTabUIState).selectedMovieType) {
                MovieListType.TOP_RATED -> SearchType.TOP_RATED
                MovieListType.POPULAR -> SearchType.POPULAR
                else -> null
            }
        } else null
    }
}