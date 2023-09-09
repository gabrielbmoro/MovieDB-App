package com.gabrielbmoro.moviedb.core.di

import com.gabrielbmoro.moviedb.BuildConfig
import com.gabrielbmoro.moviedb.core.providers.resources.AndroidResourcesProvider
import com.gabrielbmoro.moviedb.core.providers.resources.ResourcesProvider
import com.gabrielbmoro.moviedb.domain.usecases.FavoriteMovieUseCase
import com.gabrielbmoro.moviedb.domain.usecases.GetUpcomingMoviesUseCase
import com.gabrielbmoro.moviedb.domain.usecases.GetFavoriteMoviesUseCase
import com.gabrielbmoro.moviedb.domain.usecases.GetNowPlayingMoviesUseCase
import com.gabrielbmoro.moviedb.domain.usecases.GetPopularMoviesUseCase
import com.gabrielbmoro.moviedb.domain.usecases.GetTopRatedMoviesUseCase
import com.gabrielbmoro.moviedb.domain.usecases.GetTrailersUseCase
import com.gabrielbmoro.moviedb.domain.usecases.IsFavoriteMovieUseCase
import com.gabrielbmoro.moviedb.repository.di.RepositoryModule
import com.gabrielbmoro.moviedb.repository.mappers.FavoriteMovieMapper
import com.gabrielbmoro.moviedb.repository.mappers.MovieMapper
import com.gabrielbmoro.moviedb.repository.mappers.PageMapper
import com.gabrielbmoro.moviedb.repository.mappers.VideoStreamMapper
import com.gabrielbmoro.moviedb.repository.model.Movie
import com.gabrielbmoro.moviedb.ui.screens.details.DetailsScreenViewModel
import com.gabrielbmoro.moviedb.ui.screens.movies.MoviesViewModel
import com.gabrielbmoro.moviedb.ui.screens.wishlist.WishlistViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

object AppModule {
    private val useCasesModule = module {
        factory { IsFavoriteMovieUseCase(get()) }

        factory { FavoriteMovieUseCase(get()) }

        factory { GetFavoriteMoviesUseCase(get()) }

        factory { GetPopularMoviesUseCase(get()) }

        factory { GetTopRatedMoviesUseCase(get()) }

        factory { GetUpcomingMoviesUseCase(get()) }

        factory { GetNowPlayingMoviesUseCase(get()) }

        factory { GetTrailersUseCase(get()) }
    }

    private val viewModelsModule = module {
        viewModel { (movie: Movie) ->
            DetailsScreenViewModel(
                movie = movie,
                favoriteMovieUseCase = get(),
                isFavoriteMovieUseCase = get(),
                getTrailersUseCase = get()
            )
        }

        viewModel {
            MoviesViewModel(
                getTopRatedMoviesUseCase = get(),
                getUpcomingMoviesUseCase = get(),
                getPopularMoviesUseCase = get(),
                getNowPlayingMoviesUseCase = get(),
                resourcesProvider = get()
            )
        }

        viewModel {
            WishlistViewModel(
                getFavoriteMoviesUseCase = get()
            )
        }
    }

    val list = listOf(
        useCasesModule,
        viewModelsModule,
    ).plus(
        CoreModule.all()
    ).plus(
        RepositoryModule(
            BuildConfig.API_TOKEN
        ).all()
    )
}