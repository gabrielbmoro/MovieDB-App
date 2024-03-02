package com.gabrielbmoro.moviedb.repository.di

import androidx.room.Room
import com.gabrielbmoro.moviedb.domain.MoviesRepository
import com.gabrielbmoro.moviedb.repository.MoviesRepositoryImpl
import com.gabrielbmoro.moviedb.repository.datasources.retrofit.ApiRepository
import com.gabrielbmoro.moviedb.repository.datasources.retrofit.interceptors.AuthenticationInterceptor
import com.gabrielbmoro.moviedb.repository.datasources.retrofit.interceptors.LoggedInterceptor
import com.gabrielbmoro.moviedb.repository.datasources.room.DataBaseFactory
import com.gabrielbmoro.moviedb.repository.datasources.room.MIGRATION_1_2
import com.gabrielbmoro.moviedb.repository.mappers.FavoriteMovieMapper
import com.gabrielbmoro.moviedb.repository.mappers.MovieMapper
import com.gabrielbmoro.moviedb.repository.mappers.PageMapper
import com.gabrielbmoro.moviedb.repository.mappers.VideoDetailsMapper
import com.gabrielbmoro.moviedb.repository.mappers.VideoStreamMapper
import okhttp3.OkHttpClient
import org.koin.android.ext.koin.androidApplication
import org.koin.core.qualifier.named
import org.koin.dsl.bind
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


val dataModule = module {
    single {
        Room.databaseBuilder(
            androidApplication(),
            DataBaseFactory::class.java,
            "MovieDBAppDataBase"
        )
            .addMigrations(MIGRATION_1_2)
            .fallbackToDestructiveMigration()
            .build()
            .favoriteMoviesDAO()
    }

    single {
        OkHttpClient.Builder()
            .connectTimeout(15, TimeUnit.SECONDS)
            .readTimeout(15, TimeUnit.SECONDS)
            .addInterceptor(
                AuthenticationInterceptor(get(named("api_token")))
            )
            .addInterceptor(LoggedInterceptor())
            .build()
    }

    single {
        Retrofit.Builder()
            .baseUrl("https://api.themoviedb.org/3/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(get())
            .build()
            .create(ApiRepository::class.java)
    }

    single {
        MoviesRepositoryImpl(
            api = get(),
            favoriteMoviesDAO = get(),
            favoriteMoviesMapper = get(),
            pageMapper = get(),
            movieMapper = get(),
            videoDetailsMapper = get(),
            videoStreamMapper = get()
        )
    }.bind(MoviesRepository::class)

    factory { FavoriteMovieMapper() }

    factory {
        PageMapper(
            movieMapper = get()
        )
    }

    factory { MovieMapper() }

    factory { VideoDetailsMapper() }

    factory { VideoStreamMapper() }
}