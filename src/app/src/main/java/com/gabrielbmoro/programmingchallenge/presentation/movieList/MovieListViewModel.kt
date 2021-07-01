package com.gabrielbmoro.programmingchallenge.presentation.movieList

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gabrielbmoro.programmingchallenge.domain.model.Movie
import com.gabrielbmoro.programmingchallenge.domain.model.MovieListType
import com.gabrielbmoro.programmingchallenge.domain.usecase.GetPopularMoviesUseCase
import com.gabrielbmoro.programmingchallenge.domain.usecase.GetTopRatedMoviesUseCase
import kotlinx.coroutines.launch
import timber.log.Timber
import java.util.concurrent.locks.ReentrantLock

class MovieListViewModel(
    val type: MovieListType,
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
        requestMore()
    }

    fun requestMore() {
        var hasMorePages = false
        if (!lock.isLocked) {
            lock.lock()

            viewModelScope.launch {
                try {
                    isLoadingMutableLiveData.postValue(true)

                    when (type) {
                        MovieListType.TopRated -> {
                            getTopRatedMoviesUseCase.execute(currentPage)?.also {
                                hasMorePages = it.hasMorePages
                            }?.movies
                        }
                        MovieListType.Popular -> {
                            getPopularMoviesUseCase.execute(currentPage)?.also {
                                hasMorePages = it.hasMorePages
                            }?.movies
                        }
                        else -> null
                    }?.let { movies ->
                        if (hasMorePages)
                            currentPage++
                        previousSize = moviesMutableLiveData.value?.size ?: 0

                        if (moviesMutableLiveData.value == null) {
                            moviesMutableLiveData.postValue(movies)
                        } else {
                            moviesMutableLiveData.postValue(
                                moviesMutableLiveData.value?.toMutableList()?.apply {
                                    addAll(movies)
                                }?.toList() ?: emptyList()
                            )
                        }

                        if(moviesMutableLiveData.value?.isNullOrEmpty() == true) {
                            emptyStateMutableLiveData.postValue(Unit)
                        }
                    }
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

    fun reload() {
        currentPage = FIRST_PAGE
        moviesMutableLiveData.value = emptyList()
    }

    companion object {
        const val FIRST_PAGE = 1
    }
}