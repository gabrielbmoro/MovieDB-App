package com.gabrielbmoro.moviedb.core.di

import androidx.room.Room
import com.gabrielbmoro.moviedb.BuildConfig
import com.gabrielbmoro.moviedb.core.providers.resources.AndroidResourcesProvider
import com.gabrielbmoro.moviedb.core.providers.resources.ResourcesProvider
import com.gabrielbmoro.moviedb.domain.model.Movie
import com.gabrielbmoro.moviedb.domain.usecases.FavoriteMovieUseCase
import com.gabrielbmoro.moviedb.domain.usecases.GetUpcomingMoviesUseCase
import com.gabrielbmoro.moviedb.domain.usecases.GetFavoriteMoviesUseCase
import com.gabrielbmoro.moviedb.domain.usecases.GetPopularMoviesUseCase
import com.gabrielbmoro.moviedb.domain.usecases.GetTopRatedMoviesUseCase
import com.gabrielbmoro.moviedb.domain.usecases.GetTrailersUseCase
import com.gabrielbmoro.moviedb.domain.usecases.IsFavoriteMovieUseCase
import com.gabrielbmoro.moviedb.repository.MoviesRepository
import com.gabrielbmoro.moviedb.repository.MoviesRepositoryImpl
import com.gabrielbmoro.moviedb.repository.mappers.FavoriteMovieMapper
import com.gabrielbmoro.moviedb.repository.mappers.MovieMapper
import com.gabrielbmoro.moviedb.repository.mappers.PageMapper
import com.gabrielbmoro.moviedb.repository.mappers.VideoStreamMapper
import com.gabrielbmoro.moviedb.repository.retrofit.ApiRepository
import com.gabrielbmoro.moviedb.repository.retrofit.interceptors.AuthenticationInterceptor
import com.gabrielbmoro.moviedb.repository.retrofit.interceptors.LoggedInterceptor
import com.gabrielbmoro.moviedb.repository.room.DataBaseFactory
import com.gabrielbmoro.moviedb.repository.room.MIGRATION_1_2
import com.gabrielbmoro.moviedb.ui.screens.details.DetailsScreenViewModel
import com.gabrielbmoro.moviedb.ui.screens.movies.MoviesViewModel
import com.gabrielbmoro.moviedb.ui.screens.wishlist.WishlistViewModel
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

        factory { VideoStreamMapper() }
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
            )
                .addMigrations(MIGRATION_1_2)
                .fallbackToDestructiveMigration()
                .build()
                .favoriteMoviesDAO()
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
                        .addInterceptor(
                            AuthenticationInterceptor(
                                BuildConfig.API_TOKEN
                            )
                        )
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
                favoriteMoviesDAO = get(),
                favoriteMoviesMapper = get(),
                pageMapper = get(),
                movieMapper = get(),
                videoStreamMapper = get()
            )
        }
    }

    private val useCasesModule = module {
        factory { IsFavoriteMovieUseCase(get()) }

        factory { FavoriteMovieUseCase(get()) }

        factory { GetFavoriteMoviesUseCase(get()) }

        factory { GetPopularMoviesUseCase(get()) }

        factory { GetTopRatedMoviesUseCase(get()) }

        factory { GetUpcomingMoviesUseCase(get()) }

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
                getPopularMoviesUseCase = get()
            )
        }

        viewModel {
            WishlistViewModel(
                getFavoriteMoviesUseCase = get()
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