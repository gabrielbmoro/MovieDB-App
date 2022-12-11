package com.gabrielbmoro.programmingchallenge.ui.screens.details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gabrielbmoro.programmingchallenge.domain.model.Movie
import com.gabrielbmoro.programmingchallenge.domain.usecases.FavoriteMovieUseCase
import com.gabrielbmoro.programmingchallenge.domain.usecases.UnFavoriteMovieUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailsScreenViewModel @Inject constructor(
    private val favoriteMovieUseCase: FavoriteMovieUseCase,
    private val unFavoriteMovieUseCase: UnFavoriteMovieUseCase,
) : ViewModel() {
    private val onFavoriteEventMutableLiveData = MutableLiveData<Boolean>()
    val onFavoriteEvent: LiveData<Boolean> = onFavoriteEventMutableLiveData


    fun isToFavoriteOrUnFavorite(isToFavorite: Boolean, movie: Movie) {
        viewModelScope.launch {
            val response = if (isToFavorite)
                favoriteMovieUseCase.execute(movie)
            else
                unFavoriteMovieUseCase.execute(movie.title)

            if (response.data != null) {
                movie.isFavorite = isToFavorite
                onFavoriteEventMutableLiveData.postValue(movie.isFavorite)
            }
        }
    }
}