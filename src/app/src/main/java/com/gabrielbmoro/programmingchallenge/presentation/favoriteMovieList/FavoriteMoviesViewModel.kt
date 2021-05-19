package com.gabrielbmoro.programmingchallenge.presentation.favoriteMovieList

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import com.gabrielbmoro.programmingchallenge.domain.model.Movie
import com.gabrielbmoro.programmingchallenge.domain.usecase.GetFavoriteMoviesUseCase

class FavoriteMoviesViewModel(
        private val getFavoriteMoviesUseCase: GetFavoriteMoviesUseCase
) : ViewModel() {

    fun setup(): LiveData<PagedList<Movie>>? {
        return getFavoriteMoviesUseCase.execute()
    }

}