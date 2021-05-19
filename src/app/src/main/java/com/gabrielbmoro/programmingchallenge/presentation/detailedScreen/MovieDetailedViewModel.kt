package com.gabrielbmoro.programmingchallenge.presentation.detailedScreen

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gabrielbmoro.programmingchallenge.domain.model.Movie
import com.gabrielbmoro.programmingchallenge.domain.usecase.CheckMovieIsFavoriteUseCase
import com.gabrielbmoro.programmingchallenge.domain.usecase.FavoriteMovieUseCase
import com.gabrielbmoro.programmingchallenge.domain.usecase.UnFavoriteMovieUseCase
import com.gabrielbmoro.programmingchallenge.presentation.ViewModelResult
import kotlinx.coroutines.launch

class MovieDetailedViewModel(
        val movie: Movie,
        private val favoriteMovieUseCase: FavoriteMovieUseCase,
        private val unFavoriteMovieUseCase: UnFavoriteMovieUseCase,
        private val checkMovieIsFavoriteUseCase: CheckMovieIsFavoriteUseCase
) : ViewModel() {

    val onFavoriteMovieEvent = MutableLiveData<ViewModelResult>()

    init {
        checkIfIsFavorite()
    }

    private fun checkIfIsFavorite() {
        viewModelScope.launch {
            try {
                movie.isFavorite = checkMovieIsFavoriteUseCase.execute(movie)
                onFavoriteMovieEvent.postValue(ViewModelResult.Success)
            } catch (exception: Exception) {
                onFavoriteMovieEvent.postValue(ViewModelResult.Error)
            }
        }
    }

    fun isToFavoriteOrUnFavorite(isToFavorite: Boolean) {
        viewModelScope.launch {
            try {
                if (isToFavorite)
                    favoriteMovieUseCase.execute(movie)
                else
                    unFavoriteMovieUseCase.execute(movie)
                movie.isFavorite = isToFavorite
                onFavoriteMovieEvent.postValue(ViewModelResult.Success)
            } catch (exception: Exception) {
                onFavoriteMovieEvent.postValue(ViewModelResult.Error)
            }
        }
    }
}