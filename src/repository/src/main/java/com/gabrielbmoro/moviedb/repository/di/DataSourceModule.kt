package com.gabrielbmoro.moviedb.repository.di

import android.content.Context
import androidx.room.Room
import com.gabrielbmoro.moviedb.repository.datasources.retrofit.ApiRepository
import com.gabrielbmoro.moviedb.repository.datasources.retrofit.interceptors.AuthenticationInterceptor
import com.gabrielbmoro.moviedb.repository.datasources.retrofit.interceptors.LoggedInterceptor
import com.gabrielbmoro.moviedb.repository.datasources.room.DataBaseFactory
import com.gabrielbmoro.moviedb.repository.datasources.room.FavoriteMoviesDAO
import com.gabrielbmoro.moviedb.repository.datasources.room.MIGRATION_1_2
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataSourceModule {

    @Provides
    @Singleton
    fun roomDatabase(@ApplicationContext context: Context): DataBaseFactory {
        return Room.databaseBuilder(
            context,
            DataBaseFactory::class.java,
            "MovieDBAppDataBase"
        )
            .addMigrations(MIGRATION_1_2)
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    @Singleton
    fun favoriteDao(dataBaseFactory: DataBaseFactory): FavoriteMoviesDAO {
        return dataBaseFactory.favoriteMoviesDAO()
    }

    @Provides
    @Singleton
    fun okHttpClient(
        @Named("api_token") apiToken: String
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .connectTimeout(15, TimeUnit.SECONDS)
            .readTimeout(15, TimeUnit.SECONDS)
            .addInterceptor(
                AuthenticationInterceptor(apiToken)
            )
            .addInterceptor(LoggedInterceptor())
            .build()
    }

    @Provides
    @Singleton
    fun api(okHttpClient: OkHttpClient): ApiRepository {
        return Retrofit.Builder()
            .baseUrl("https://api.themoviedb.org/3/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
            .create(ApiRepository::class.java)
    }
}