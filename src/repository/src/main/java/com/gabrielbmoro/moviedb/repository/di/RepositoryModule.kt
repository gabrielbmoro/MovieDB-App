package com.gabrielbmoro.moviedb.repository.di

import androidx.room.Room
import com.gabrielbmoro.moviedb.repository.MoviesRepository
import com.gabrielbmoro.moviedb.repository.MoviesRepositoryImpl
import com.gabrielbmoro.moviedb.repository.datasources.retrofit.ApiRepository
import com.gabrielbmoro.moviedb.repository.datasources.retrofit.interceptors.AuthenticationInterceptor
import com.gabrielbmoro.moviedb.repository.datasources.retrofit.interceptors.LoggedInterceptor
import com.gabrielbmoro.moviedb.repository.datasources.room.DataBaseFactory
import com.gabrielbmoro.moviedb.repository.datasources.room.MIGRATION_1_2
import com.gabrielbmoro.moviedb.repository.mappers.FavoriteMovieMapper
import com.gabrielbmoro.moviedb.repository.mappers.MovieMapper
import com.gabrielbmoro.moviedb.repository.mappers.PageMapper
import com.gabrielbmoro.moviedb.repository.mappers.VideoStreamMapper
import okhttp3.OkHttpClient
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class RepositoryModule(
    private val apiToken: String
) {
    val module = module {
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

        single {
            Retrofit.Builder()
                .baseUrl("https://api.themoviedb.org/3/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(
                    OkHttpClient.Builder()
                        .connectTimeout(15, TimeUnit.SECONDS)
                        .readTimeout(15, TimeUnit.SECONDS)
                        .addInterceptor(
                            AuthenticationInterceptor(apiToken)
                        )
                        .addInterceptor(LoggedInterceptor())
                        .build()
                )
                .build()
                .create(ApiRepository::class.java)
        }

        factory { MovieMapper() }

        factory { PageMapper(get()) }

        factory { FavoriteMovieMapper() }

        factory { VideoStreamMapper() }

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
}