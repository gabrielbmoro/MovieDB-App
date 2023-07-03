package com.gabrielbmoro.programmingchallenge.core.di

import androidx.room.Room
import com.gabrielbmoro.programmingchallenge.BuildConfig
import com.gabrielbmoro.programmingchallenge.core.providers.resources.AndroidResourcesProvider
import com.gabrielbmoro.programmingchallenge.core.providers.resources.ResourcesProvider
import com.gabrielbmoro.programmingchallenge.domain.model.Movie
import com.gabrielbmoro.programmingchallenge.domain.model.MovieListType
import com.gabrielbmoro.programmingchallenge.domain.usecases.FavoriteMovieUseCase
import com.gabrielbmoro.programmingchallenge.domain.usecases.GetFavoriteMoviesUseCase
import com.gabrielbmoro.programmingchallenge.domain.usecases.GetPopularMoviesUseCase
import com.gabrielbmoro.programmingchallenge.domain.usecases.GetTopRatedMoviesUseCase
import com.gabrielbmoro.programmingchallenge.domain.usecases.IsFavoriteMovieUseCase
import com.gabrielbmoro.programmingchallenge.repository.MoviesRepository
import com.gabrielbmoro.programmingchallenge.repository.MoviesRepositoryImpl
import com.gabrielbmoro.programmingchallenge.repository.mappers.FavoriteMovieMapper
import com.gabrielbmoro.programmingchallenge.repository.mappers.MovieMapper
import com.gabrielbmoro.programmingchallenge.repository.mappers.PageMapper
import com.gabrielbmoro.programmingchallenge.repository.retrofit.ApiRepository
import com.gabrielbmoro.programmingchallenge.repository.retrofit.LoggedInterceptor
import com.gabrielbmoro.programmingchallenge.repository.room.DataBaseFactory
import com.gabrielbmoro.programmingchallenge.ui.screens.details.DetailsScreenViewModel
import com.gabrielbmoro.programmingchallenge.ui.screens.home.HomeViewModel
import okhttp3.OkHttpClient
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object AppModule {
    private val mappersModule = module {
        factory { MovieMapper() }

        factory { PageMapper(get()) }

        factory { FavoriteMovieMapper() }
    }

    private val providersModule = module {
        factory<ResourcesProvider> {
            AndroidResourcesProvider(androidContext().resources)
        }
    }

    private val databaseModule = module {
        single {
            Room.databaseBuilder(
                androidContext(),
                DataBaseFactory::class.java,
                "MovieDBAppDataBase"
            ).build().favoriteMoviesDAO()
        }
    }

    private val networkModule = module {
        single {
            Retrofit.Builder()
                .baseUrl("https://api.themoviedb.org/3/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(
                    OkHttpClient.Builder()
                        .connectTimeout(15, TimeUnit.SECONDS)
                        .readTimeout(15, TimeUnit.SECONDS)
                        .addInterceptor(LoggedInterceptor())
                        .build()
                )
                .build()
                .create(ApiRepository::class.java)
        }
    }

    private val repositoryModule = module {
        single<MoviesRepository> {
            MoviesRepositoryImpl(
                api = get(),
                apiToken = BuildConfig.API_TOKEN,
                favoriteMoviesDAO = get(),
                favoriteMoviesMapper = get(),
                pageMapper = get(),
                movieMapper = get(),
            )
        }
    }

    private val useCasesModule = module {
        factory { IsFavoriteMovieUseCase(get()) }

        factory { FavoriteMovieUseCase(get()) }

        factory { GetFavoriteMoviesUseCase(get()) }

        factory { GetPopularMoviesUseCase(get()) }

        factory { GetTopRatedMoviesUseCase(get()) }
    }

    private val viewModelsModule = module {
        viewModel { (movieListType: MovieListType) ->
            HomeViewModel(
                movieListType = movieListType,
                getFavoriteMoviesUseCase = get(),
                getTopRatedMoviesUseCase = get(),
                getPopularMoviesUseCase = get()
            )
        }

        viewModel { (movie: Movie) ->
            DetailsScreenViewModel(
                movie = movie,
                favoriteMovieUseCase = get(),
                isFavoriteMovieUseCase = get()
            )
        }
    }

    val list = listOf(
        networkModule,
        databaseModule,
        repositoryModule,
        mappersModule,
        providersModule,
        useCasesModule,
        viewModelsModule
    )
}