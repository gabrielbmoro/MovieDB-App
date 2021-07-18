package com.gabrielbmoro.programmingchallenge.presentation.detailedScreen

import android.app.Application
import androidx.lifecycle.*
import com.gabrielbmoro.programmingchallenge.repository.entities.Movie
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
) : AndroidViewModel(application) {

    lateinit var movie: Movie

    private val onFavoriteEventMutableLiveData = MutableLiveData<Boolean>()
    val onFavoriteEvent : LiveData<Boolean> = onFavoriteEventMutableLiveData

    fun setup(movie: Movie) {
        this.movie = movie
    }

    fun isToFavoriteOrUnFavorite(isToFavorite: Boolean) {
        viewModelScope.launch {
            try {
                if (isToFavorite)
                    favoriteMovieUseCase.execute(movie)
                else
                    unFavoriteMovieUseCase.execute(movie)
                movie.isFavorite = isToFavorite
                onFavoriteEventMutableLiveData.postValue(movie.isFavorite)
            } catch (exception: Exception) { }
        }
    }
}