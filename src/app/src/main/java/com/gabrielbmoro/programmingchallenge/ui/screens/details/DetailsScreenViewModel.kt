package com.gabrielbmoro.programmingchallenge.ui.screens.details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gabrielbmoro.programmingchallenge.repository.entities.Movie
import com.gabrielbmoro.programmingchallenge.usecases.FavoriteMovieUseCase
import com.gabrielbmoro.programmingchallenge.usecases.UnFavoriteMovieUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailsScreenViewModel @Inject constructor(
    private val favoriteMovieUseCase: FavoriteMovieUseCase,
    private val unFavoriteMovieUseCase: UnFavoriteMovieUseCase,
) : ViewModel() {
    private val onFavoriteEventMutableLiveData = MutableLiveData<Boolean>()
    val onFavoriteEvent : LiveData<Boolean> = onFavoriteEventMutableLiveData


    fun isToFavoriteOrUnFavorite(isToFavorite: Boolean, movie: Movie) {
        viewModelScope.launch {
            try {
                if (isToFavorite)
                    favoriteMovieUseCase.execute(movie)
                else
                    unFavoriteMovieUseCase.execute(movie.title)
                movie.isFavorite = isToFavorite
                onFavoriteEventMutableLiveData.postValue(movie.isFavorite)
            } catch (exception: Exception) { }
        }
    }
}