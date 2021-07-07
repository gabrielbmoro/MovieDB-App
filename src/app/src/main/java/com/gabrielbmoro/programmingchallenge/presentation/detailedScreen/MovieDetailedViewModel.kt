package com.gabrielbmoro.programmingchallenge.presentation.detailedScreen

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.gabrielbmoro.programmingchallenge.repository.entities.Movie
import com.gabrielbmoro.programmingchallenge.usecases.CheckMovieIsFavoriteUseCase
import com.gabrielbmoro.programmingchallenge.usecases.FavoriteMovieUseCase
import com.gabrielbmoro.programmingchallenge.usecases.UnFavoriteMovieUseCase
import dagger.assisted.AssistedFactory
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieDetailedViewModel @Inject constructor(
    private val favoriteMovieUseCase: FavoriteMovieUseCase,
    private val unFavoriteMovieUseCase: UnFavoriteMovieUseCase,
    private val checkMovieIsFavoriteUseCase: CheckMovieIsFavoriteUseCase,
) : ViewModel() {

    lateinit var movie: Movie
    val onFavoriteMovieEvent = MutableLiveData<ViewModelResult>()

    fun setup(movie: Movie) {
        this.movie = movie
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

    sealed class ViewModelResult {
        object Success : ViewModelResult()
        object Error : ViewModelResult()
    }
}