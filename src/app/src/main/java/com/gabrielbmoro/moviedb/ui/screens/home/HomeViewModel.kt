package com.gabrielbmoro.moviedb.ui.screens.home

import androidx.lifecycle.*
import com.gabrielbmoro.moviedb.domain.model.MovieListType
import com.gabrielbmoro.moviedb.domain.usecases.GetFavoriteMoviesUseCase
import com.gabrielbmoro.moviedb.domain.usecases.GetPopularMoviesUseCase
import com.gabrielbmoro.moviedb.domain.usecases.GetTopRatedMoviesUseCase
import com.gabrielbmoro.moviedb.ui.common.widgets.SearchType
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class HomeViewModel(
    private val movieListType: MovieListType,
    private val getFavoriteMoviesUseCase: GetFavoriteMoviesUseCase,
    private val getTopRatedMoviesUseCase: GetTopRatedMoviesUseCase,
    private val getPopularMoviesUseCase: GetPopularMoviesUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(
        HomeUIState(
            selectedMovieType = movieListType,
            paginatedMovies = emptyFlow(),
            favoriteMovies = null,
            isLoading = false
        )
    )
    val uiState = _uiState.stateIn(
        viewModelScope,
        SharingStarted.Eagerly,
        _uiState.value
    )

    init {
        when(movieListType) {
            MovieListType.FAVORITE -> loadFavoriteMovies()
            else -> loadBy(movieListType)
        }
    }

    fun setup() {
        if(movieListType == MovieListType.FAVORITE) {
            loadFavoriteMovies()
        }
    }

    private fun loadFavoriteMovies() {
        viewModelScope.launch {
            _uiState.update {
                it.copy(
                    isLoading = true,
                    favoriteMovies = null
                )
            }

            _uiState.update {
                it.copy(
                    isLoading = false,
                    favoriteMovies = getFavoriteMoviesUseCase().data
                )
            }
        }.invokeOnCompletion {
            _uiState.update {
                it.copy(
                    isLoading = false
                )
            }
        }
    }


    fun onSearchBy(searchType: SearchType) {
        _uiState.update {
            val (movieListType, paginatedData) = when (searchType) {
                SearchType.TOP_RATED -> Pair(
                    MovieListType.TOP_RATED,
                    getTopRatedMoviesUseCase()
                )
                SearchType.POPULAR -> Pair(MovieListType.POPULAR, getPopularMoviesUseCase())
            }
            it.copy(
                selectedMovieType = movieListType,
                paginatedMovies = paginatedData
            )
        }
    }

    private fun loadBy(listType: MovieListType){
        _uiState.update {
            val paginatedData = when (listType) {
                MovieListType.TOP_RATED -> getTopRatedMoviesUseCase()
                MovieListType.POPULAR -> getPopularMoviesUseCase()
                else -> emptyFlow()
            }
            it.copy(
                selectedMovieType = listType,
                paginatedMovies = paginatedData
            )
        }
    }

    fun currentSearchType(): SearchType? {
        return when (_uiState.value.selectedMovieType) {
            MovieListType.TOP_RATED -> SearchType.TOP_RATED
            MovieListType.POPULAR -> SearchType.POPULAR
            else -> null
        }
    }
}