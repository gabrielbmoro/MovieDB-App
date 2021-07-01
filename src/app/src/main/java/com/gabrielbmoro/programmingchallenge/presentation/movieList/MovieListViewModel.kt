package com.gabrielbmoro.programmingchallenge.presentation.movieList

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gabrielbmoro.programmingchallenge.repository.entities.MovieListType
import com.gabrielbmoro.programmingchallenge.domain.usecase.GetFavoriteMoviesUseCase
import com.gabrielbmoro.programmingchallenge.domain.usecase.GetPopularMoviesUseCase
import com.gabrielbmoro.programmingchallenge.domain.usecase.GetTopRatedMoviesUseCase
import com.gabrielbmoro.programmingchallenge.repository.entities.Movie
import com.gabrielbmoro.programmingchallenge.repository.entities.PageMovies
import kotlinx.coroutines.launch
import timber.log.Timber
import java.util.concurrent.locks.ReentrantLock

class MovieListViewModel(
    val type: MovieListType,
    private val getFavoriteMoviesUseCase: GetFavoriteMoviesUseCase,
    private val getTopRatedMoviesUseCase: GetTopRatedMoviesUseCase,
    private val getPopularMoviesUseCase: GetPopularMoviesUseCase
) : ViewModel() {

    private val emptyStateMutableLiveData = MutableLiveData<Unit>()
    val emptyStateLiveData: LiveData<Unit> = emptyStateMutableLiveData

    private val errorStateMutableLiveData = MutableLiveData<Unit>()
    val errorStateLiveData: LiveData<Unit> = errorStateMutableLiveData

    private val moviesMutableLiveData = MutableLiveData<List<Movie>>()
    val moviesLiveData: LiveData<List<Movie>> = moviesMutableLiveData

    private val isLoadingMutableLiveData = MutableLiveData<Boolean>()
    val isLoadingLiveData: LiveData<Boolean> = isLoadingMutableLiveData

    //region pagination
    var currentPage = FIRST_PAGE
        private set
    var previousSize = moviesMutableLiveData.value?.size ?: 0
        private set
    private val lock = ReentrantLock()
    //endregion

    init {
        load()
    }

    private fun hasMorePages(pageMovies: PageMovies): Boolean {
        return currentPage < pageMovies.totalPages
    }

    fun load() {
        if (!lock.isLocked) {
            lock.lock()

            viewModelScope.launch {
                try {
                    isLoadingMutableLiveData.postValue(true)

                    when (type) {
                        MovieListType.TopRated, MovieListType.Popular -> loadTopRatedOrPopularMovies()
                        MovieListType.Favorite -> loadFavoriteMovies()
                    }

                    checkEmptyState()
                } catch (exception: Exception) {
                    Timber.e(exception)
                    errorStateMutableLiveData.postValue(Unit)
                } finally {
                    isLoadingMutableLiveData.postValue(false)
                }
            }
            lock.unlock()
        }
    }

    private fun checkEmptyState() {
        if (moviesMutableLiveData.value?.isNullOrEmpty() == true) {
            emptyStateMutableLiveData.postValue(Unit)
        }
    }

    private suspend fun loadFavoriteMovies() {
        val movies = getFavoriteMoviesUseCase.execute()
        moviesMutableLiveData.postValue(movies)
    }

    private suspend fun loadTopRatedOrPopularMovies() {
        when (type) {
            MovieListType.TopRated -> {
                getTopRatedMoviesUseCase.execute(currentPage)
            }
            MovieListType.Popular -> {
                getPopularMoviesUseCase.execute(currentPage)
            }
            else -> {
                Timber.d("Nothing to do - It is not top rated or popular movie")
                null
            }
        }?.let { page ->
            if (hasMorePages(page))
                currentPage++
            previousSize = moviesMutableLiveData.value?.size ?: 0

            if (moviesMutableLiveData.value == null) {
                moviesMutableLiveData.postValue(page.results ?: emptyList())
            } else {
                moviesMutableLiveData.postValue(
                    moviesMutableLiveData.value?.toMutableList()?.apply {
                        addAll(page.results ?: emptyList())
                    }?.toList() ?: emptyList()
                )
            }
        }
    }

    fun reload() {
        currentPage = FIRST_PAGE
        moviesMutableLiveData.value = emptyList()
    }

    companion object {
        const val FIRST_PAGE = 1
    }
}