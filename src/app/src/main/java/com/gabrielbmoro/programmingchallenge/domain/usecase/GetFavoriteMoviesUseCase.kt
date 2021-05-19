package com.gabrielbmoro.programmingchallenge.domain.usecase

import androidx.lifecycle.LiveData
import androidx.paging.PagedList
import androidx.paging.toLiveData
import com.gabrielbmoro.programmingchallenge.domain.model.Movie
import com.gabrielbmoro.programmingchallenge.repository.MoviesRepository

class GetFavoriteMoviesUseCase(val repository: MoviesRepository) {

    fun execute(): LiveData<PagedList<Movie>>? {
        return repository.getFavoriteMovies()?.toLiveData(PAGE_SIZE)
    }

    companion object {
        private const val PAGE_SIZE = 10
    }
}