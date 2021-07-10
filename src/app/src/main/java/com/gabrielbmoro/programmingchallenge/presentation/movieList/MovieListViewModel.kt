package com.gabrielbmoro.programmingchallenge.presentation.movieList

import android.app.Application
import androidx.lifecycle.*
import com.gabrielbmoro.programmingchallenge.repository.entities.MovieListType
import com.gabrielbmoro.programmingchallenge.usecases.GetFavoriteMoviesUseCase
import com.gabrielbmoro.programmingchallenge.usecases.GetPopularMoviesUseCase
import com.gabrielbmoro.programmingchallenge.usecases.GetTopRatedMoviesUseCase
import com.gabrielbmoro.programmingchallenge.repository.entities.Movie
import com.gabrielbmoro.programmingchallenge.repository.entities.PageMovies
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
import java.util.concurrent.locks.ReentrantLock
import javax.inject.Inject

@HiltViewModel
class MovieListViewModel @Inject constructor(
    application: Application,
    private val getFavoriteMoviesUseCase: GetFavoriteMoviesUseCase,
    private val getTopRatedMoviesUseCase: GetTopRatedMoviesUseCase,
    private val getPopularMoviesUseCase: GetPopularMoviesUseCase
) : AndroidViewModel(application) {

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

    lateinit var type: MovieListType

    fun setup(type: MovieListType) {
        this.type = type
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
            if (hasMorePages(page)) {
                currentPage++
                previousSize = moviesMutableLiveData.value?.size ?: 0
            }

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

        load()
    }

    companion object {
        const val FIRST_PAGE = 1
    }
}