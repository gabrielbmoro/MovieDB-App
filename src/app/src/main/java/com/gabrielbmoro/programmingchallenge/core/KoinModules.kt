package com.gabrielbmoro.programmingchallenge.core

import androidx.room.Room
import com.gabrielbmoro.programmingchallenge.repository.entities.MovieListType
import com.gabrielbmoro.programmingchallenge.usecase.*
import com.gabrielbmoro.programmingchallenge.presentation.MainViewModel
import com.gabrielbmoro.programmingchallenge.presentation.detailedScreen.MovieDetailedViewModel
import com.gabrielbmoro.programmingchallenge.presentation.movieList.MovieListViewModel
import com.gabrielbmoro.programmingchallenge.repository.MoviesRepository
import com.gabrielbmoro.programmingchallenge.repository.MoviesRepositoryImpl
import com.gabrielbmoro.programmingchallenge.repository.entities.Movie
import com.gabrielbmoro.programmingchallenge.repository.retrofit.ApiRepository
import com.gabrielbmoro.programmingchallenge.repository.retrofit.LoggedInterceptor
import com.gabrielbmoro.programmingchallenge.repository.room.DataBaseFactory
import okhttp3.OkHttpClient
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.bind
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


val repositoryModule = module {
    single {
        MoviesRepositoryImpl(
            api = Retrofit.Builder()
                .baseUrl(ConfigVariables.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(
                    OkHttpClient.Builder()
                        .connectTimeout(15, TimeUnit.SECONDS)
                        .readTimeout(15, TimeUnit.SECONDS)
                        .addInterceptor(LoggedInterceptor())
                        .build()
                )
                .build()
                .create(ApiRepository::class.java),
            favoriteMoviesDAO = Room.databaseBuilder(
                androidContext(),
                DataBaseFactory::class.java,
                ConfigVariables.DATABASE_NAME
            ).build().favoriteMoviesDAO()
        )
    } bind MoviesRepository::class
}

val usecaseModule = module {
    single { GetTopRatedMoviesUseCase(get()) }
    single { GetPopularMoviesUseCase(get()) }
    single { GetFavoriteMoviesUseCase(get()) }
    single { FavoriteMovieUseCase(get()) }
    single { UnFavoriteMovieUseCase(get()) }
    single { CheckMovieIsFavoriteUseCase(get()) }
}

val viewModelModules = module {
    viewModel { (type: MovieListType) -> MovieListViewModel(type, get(), get(), get()) }
    viewModel { (movie: Movie) ->
        MovieDetailedViewModel(movie, get(), get(), get())
    }
    viewModel { MainViewModel() }
}