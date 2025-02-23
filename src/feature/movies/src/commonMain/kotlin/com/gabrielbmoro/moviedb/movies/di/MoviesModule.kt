package com.gabrielbmoro.moviedb.movies.di

import com.gabrielbmoro.moviedb.domain.entities.Movie
import com.gabrielbmoro.moviedb.movies.domain.interactor.MoviesInteractor
import com.gabrielbmoro.moviedb.movies.domain.mapper.MovieMapper
import com.gabrielbmoro.moviedb.movies.domain.model.MoviesPaging
import com.gabrielbmoro.moviedb.movies.domain.state.MoviesStateHolder
import com.gabrielbmoro.moviedb.movies.domain.state.MoviesStateHolderImpl
import com.gabrielbmoro.moviedb.movies.domain.usecase.GetFilterTypeOrder
import com.gabrielbmoro.moviedb.movies.domain.usecase.impl.GetFilterTypeOrderImpl
import com.gabrielbmoro.moviedb.movies.domain.usecase.GetFirstMoviesStateUseCase
import com.gabrielbmoro.moviedb.movies.domain.usecase.impl.GetFirstMoviesStateUseCaseImpl
import com.gabrielbmoro.moviedb.movies.domain.usecase.GetMoviesFromFilterUseCase
import com.gabrielbmoro.moviedb.movies.domain.usecase.GetMoviesStateFromFilterSelectionUseCase
import com.gabrielbmoro.moviedb.movies.domain.usecase.GetMoviesStateFromPaginationUseCase
import com.gabrielbmoro.moviedb.movies.domain.usecase.GetUpdatedMoviesSuccessStateUseCase
import com.gabrielbmoro.moviedb.movies.domain.usecase.UpdateSelectedMenuItemUseCase
import com.gabrielbmoro.moviedb.movies.domain.usecase.impl.GetMoviesFromFilterUseCaseImpl
import com.gabrielbmoro.moviedb.movies.domain.usecase.impl.GetMoviesStateFromFilterSelectionUseCaseImpl
import com.gabrielbmoro.moviedb.movies.domain.usecase.impl.GetMoviesStateFromPaginationUseCaseImpl
import com.gabrielbmoro.moviedb.movies.domain.usecase.impl.GetUpdatedMoviesSuccessStateUseCaseImpl
import com.gabrielbmoro.moviedb.movies.domain.usecase.impl.UpdateSelectedMenuItemUseCaseImpl
import com.gabrielbmoro.moviedb.movies.domain.usecase.GetFirstSuccessStateUseCase
import com.gabrielbmoro.moviedb.movies.domain.usecase.ListenToPaginationUseCase
import com.gabrielbmoro.moviedb.movies.domain.usecase.OnEndScrollUseCase
import com.gabrielbmoro.moviedb.movies.domain.usecase.OnSelectFilterUseCase
import com.gabrielbmoro.moviedb.movies.domain.usecase.UpdateStateBasedOnNextPageUseCase
import com.gabrielbmoro.moviedb.movies.domain.usecase.UpdateToLoadedFromFilterUseCase
import com.gabrielbmoro.moviedb.movies.domain.usecase.UpdateToLoadingStateUseCase
import com.gabrielbmoro.moviedb.movies.domain.usecase.impl.GetFirstSuccessStateUseCaseImpl
import com.gabrielbmoro.moviedb.movies.domain.usecase.impl.ListenToPaginationUseCaseImpl
import com.gabrielbmoro.moviedb.movies.domain.usecase.impl.OnEndScrollUseCaseImpl
import com.gabrielbmoro.moviedb.movies.domain.usecase.impl.OnSelectFilterUseCaseImpl
import com.gabrielbmoro.moviedb.movies.domain.usecase.impl.UpdateStateBasedOnNextPageUseCaseImpl
import com.gabrielbmoro.moviedb.movies.domain.usecase.impl.UpdateToLoadedFromFilterUseCaseImpl
import com.gabrielbmoro.moviedb.movies.domain.usecase.impl.UpdateToLoadingStateUseCaseImpl
import com.gabrielbmoro.moviedb.movies.ui.screens.movies.MovieCardInfo
import com.gabrielbmoro.moviedb.movies.ui.screens.movies.MoviesViewModel
import com.gabrielbmoro.moviedb.platform.Mapper
import com.gabrielbmoro.moviedb.platform.paging.PagingController
import com.gabrielbmoro.moviedb.platform.paging.SimplePaging
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import org.koin.core.module.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.lazyModule

val featureMoviesModule = lazyModule {

    factory<GetMoviesFromFilterUseCase> {
        GetMoviesFromFilterUseCaseImpl(get())
    }

    factory<GetFilterTypeOrder> {
        GetFilterTypeOrderImpl()
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

    factory<UpdateStateBasedOnNextPageUseCase> {
        UpdateStateBasedOnNextPageUseCaseImpl(
            stateHolder = get(),
            getMoviesStateFromPaginationUseCase = get(),
        )
    }

    factory<OnSelectFilterUseCase> {
        OnSelectFilterUseCaseImpl(
            updateToLoadingState = get(),
            updateToLoadedFromFilter = get(),
            pagingController = get(named(MOVIES_PAGING_CONTROLLER)),
        )
    }

    factory<UpdateToLoadedFromFilterUseCase> {
        UpdateToLoadedFromFilterUseCaseImpl(
            getMoviesStateFromFilterSelection = get(),
            stateHolder = get(),
        )
    }

    factory<OnEndScrollUseCase> {
        OnEndScrollUseCaseImpl(
            pagingController = get(named(MOVIES_PAGING_CONTROLLER)),
        )
    }

    factory {
        MoviesInteractor(
            onSelectFilter = get(),
            listenToPagination = get(),
            onEndScroll = get(),
        )
    }

    factory<ListenToPaginationUseCase> {
        ListenToPaginationUseCaseImpl(
            pagingController = get(named(MOVIES_PAGING_CONTROLLER)),
            updateStateBasedOnNextPage = get(),
        )
    }

    single<PagingController>(named(MOVIES_PAGING_CONTROLLER)) {
        MoviesPaging()
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

private const val MOVIES_PAGING_CONTROLLER = "movies paging controller"
