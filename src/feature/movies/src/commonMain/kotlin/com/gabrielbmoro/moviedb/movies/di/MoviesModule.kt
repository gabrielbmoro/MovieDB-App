package com.gabrielbmoro.moviedb.movies.di

import com.gabrielbmoro.moviedb.domain.entities.Movie
import com.gabrielbmoro.moviedb.movies.data.MoviesPageDataSource
import com.gabrielbmoro.moviedb.movies.data.MoviesPageRepositoryImpl
import com.gabrielbmoro.moviedb.movies.domain.interactor.MoviesInteractor
import com.gabrielbmoro.moviedb.movies.domain.mapper.MovieMapper
import com.gabrielbmoro.moviedb.movies.domain.repository.MoviesPageRepository
import com.gabrielbmoro.moviedb.movies.domain.state.MoviesStateHolder
import com.gabrielbmoro.moviedb.movies.domain.state.MoviesStateHolderImpl
import com.gabrielbmoro.moviedb.movies.domain.usecase.FetchFirstPageUseCase
import com.gabrielbmoro.moviedb.movies.domain.usecase.GetFilterTypeOrderUseCase
import com.gabrielbmoro.moviedb.movies.domain.usecase.GetFirstMoviesStateUseCase
import com.gabrielbmoro.moviedb.movies.domain.usecase.GetFirstSuccessStateUseCase
import com.gabrielbmoro.moviedb.movies.domain.usecase.GetMoviesFromFilterUseCase
import com.gabrielbmoro.moviedb.movies.domain.usecase.GetMoviesStateFromFilterSelectionUseCase
import com.gabrielbmoro.moviedb.movies.domain.usecase.GetMoviesStateFromPaginationUseCase
import com.gabrielbmoro.moviedb.movies.domain.usecase.GetUpdatedMoviesSuccessStateUseCase
import com.gabrielbmoro.moviedb.movies.domain.usecase.OnRequestMoreMoviesUseCase
import com.gabrielbmoro.moviedb.movies.domain.usecase.OnSelectFilterUseCase
import com.gabrielbmoro.moviedb.movies.domain.usecase.UpdateSelectedMenuItemUseCase
import com.gabrielbmoro.moviedb.movies.domain.usecase.UpdateStateBasedOnCurrentPageUseCase
import com.gabrielbmoro.moviedb.movies.domain.usecase.UpdateToLoadedFromFilterUseCase
import com.gabrielbmoro.moviedb.movies.domain.usecase.UpdateToLoadingStateUseCase
import com.gabrielbmoro.moviedb.movies.domain.usecase.impl.FetchFirstPageUseCaseImpl
import com.gabrielbmoro.moviedb.movies.domain.usecase.impl.GetFilterTypeOrderUseCaseImpl
import com.gabrielbmoro.moviedb.movies.domain.usecase.impl.GetFirstMoviesStateUseCaseImpl
import com.gabrielbmoro.moviedb.movies.domain.usecase.impl.GetFirstSuccessStateUseCaseImpl
import com.gabrielbmoro.moviedb.movies.domain.usecase.impl.GetMoviesFromFilterUseCaseImpl
import com.gabrielbmoro.moviedb.movies.domain.usecase.impl.GetMoviesStateFromFilterSelectionUseCaseImpl
import com.gabrielbmoro.moviedb.movies.domain.usecase.impl.GetMoviesStateFromPaginationUseCaseImpl
import com.gabrielbmoro.moviedb.movies.domain.usecase.impl.GetUpdatedMoviesSuccessStateUseCaseImpl
import com.gabrielbmoro.moviedb.movies.domain.usecase.impl.OnEndScrollUseCaseImpl
import com.gabrielbmoro.moviedb.movies.domain.usecase.impl.OnSelectFilterUseCaseImpl
import com.gabrielbmoro.moviedb.movies.domain.usecase.impl.UpdateSelectedMenuItemUseCaseImpl
import com.gabrielbmoro.moviedb.movies.domain.usecase.impl.UpdateStateBasedOnCurrentPageUseCaseImpl
import com.gabrielbmoro.moviedb.movies.domain.usecase.impl.UpdateToLoadedFromFilterUseCaseImpl
import com.gabrielbmoro.moviedb.movies.domain.usecase.impl.UpdateToLoadingStateUseCaseImpl
import com.gabrielbmoro.moviedb.movies.ui.screens.movies.MovieCardInfo
import com.gabrielbmoro.moviedb.movies.ui.screens.movies.MoviesViewModel
import com.gabrielbmoro.moviedb.platform.Mapper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.lazyModule

val featureMoviesModule = lazyModule {

    factory<GetMoviesFromFilterUseCase> {
        GetMoviesFromFilterUseCaseImpl(get())
    }

    factory<GetFilterTypeOrderUseCase> {
        GetFilterTypeOrderUseCaseImpl()
    }

    factory<GetFirstMoviesStateUseCase> {
        GetFirstMoviesStateUseCaseImpl(
            getFilterTypeOrder = get(),
        )
    }

    factory<UpdateSelectedMenuItemUseCase> {
        UpdateSelectedMenuItemUseCaseImpl()
    }

    factory<GetMoviesStateFromPaginationUseCase> {
        GetMoviesStateFromPaginationUseCaseImpl(
            getMoviesFromFilter = get(),
            getUpdatedMoviesSuccessState = get(),
        )
    }

    factory<GetMoviesStateFromFilterSelectionUseCase> {
        GetMoviesStateFromFilterSelectionUseCaseImpl(
            getMoviesFromFilter = get(),
            movieMapper = get(),
        )
    }

    factory<UpdateToLoadingStateUseCase> {
        UpdateToLoadingStateUseCaseImpl(
            stateHolder = get(),
            updateSelectedMenuItemUseCase = get(),
        )
    }

    factory<GetUpdatedMoviesSuccessStateUseCase> {
        GetUpdatedMoviesSuccessStateUseCaseImpl(
            updateSelectedMenuItemUseCase = get(),
            getFirstSuccessStateUseCase = get(),
            movieMapper = get(),
        )
    }

    factory<GetFirstSuccessStateUseCase> {
        GetFirstSuccessStateUseCaseImpl(
            movieMapper = get(),
            getFilterTypeOrder = get(),
        )
    }

    factory<Mapper<Movie, MovieCardInfo>> {
        MovieMapper()
    }

    factory<UpdateStateBasedOnCurrentPageUseCase> {
        UpdateStateBasedOnCurrentPageUseCaseImpl(
            stateHolder = get(),
            getMoviesStateFromPagination = get(),
            repository = get(),
        )
    }

    factory<OnSelectFilterUseCase> {
        OnSelectFilterUseCaseImpl(
            updateToLoadingState = get(),
            updateToLoadedFromFilter = get(),
            pageRepository = get(),
        )
    }

    factory<UpdateToLoadedFromFilterUseCase> {
        UpdateToLoadedFromFilterUseCaseImpl(
            getMoviesStateFromFilterSelection = get(),
            stateHolder = get(),
        )
    }

    factory<OnRequestMoreMoviesUseCase> {
        OnEndScrollUseCaseImpl(
            pageRepository = get(),
            updateStateBasedOnCurrentPage = get(),
        )
    }

    factory<MoviesPageRepository> {
        MoviesPageRepositoryImpl(get())
    }

    single {
        MoviesPageDataSource()
    }

    factory {
        MoviesInteractor(
            onSelectFilter = get(),
            fetchFirstPage = get(),
            onRequestMoreMovies = get(),
        )
    }

    factory<FetchFirstPageUseCase> {
        FetchFirstPageUseCaseImpl(
            repository = get(),
            updateStateBasedOCurrentPage = get(),
        )
    }

    single<MoviesStateHolder> {
        MoviesStateHolderImpl(
            getFirstMoviesState = get(),
        )
    }

    viewModel {
        MoviesViewModel(
            interactor = get(),
            uiState = get<MoviesStateHolder>().state,
            ioDispatcher = Dispatchers.IO,
        )
    }
}
