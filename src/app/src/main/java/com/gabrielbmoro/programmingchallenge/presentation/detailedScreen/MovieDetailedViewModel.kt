package com.gabrielbmoro.programmingchallenge.presentation.detailedScreen

import android.app.Application
import androidx.lifecycle.*
import com.gabrielbmoro.programmingchallenge.repository.entities.Movie
import com.gabrielbmoro.programmingchallenge.usecases.CheckMovieIsFavoriteUseCase
import com.gabrielbmoro.programmingchallenge.usecases.FavoriteMovieUseCase
import com.gabrielbmoro.programmingchallenge.usecases.UnFavoriteMovieUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieDetailedViewModel @Inject constructor(
    application: Application,
    private val favoriteMovieUseCase: FavoriteMovieUseCase,
    private val unFavoriteMovieUseCase: UnFavoriteMovieUseCase,
    private val checkMovieIsFavoriteUseCase: CheckMovieIsFavoriteUseCase,
) : AndroidViewModel(application) {

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