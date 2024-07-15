package com.gabrielbmoro.moviedb.movies.di

import cafe.adriel.voyager.core.screen.Screen
import com.gabrielbmoro.moviedb.movies.ui.screens.movies.MoviesScreen
import com.gabrielbmoro.moviedb.platform.navigation.NavigationDestinations
import org.koin.core.annotation.ComponentScan
import org.koin.core.annotation.Factory
import org.koin.core.annotation.Module
import org.koin.core.annotation.Named
import org.koin.ksp.generated.module

val featureMoviesModule = MoviesModule().module

@Module
@ComponentScan("com.gabrielbmoro.moviedb.movies.ui")
class MoviesModule {

    @Factory
    @Named(NavigationDestinations.MOVIES)
    fun moviesScreen(): Screen {
        return MoviesScreen()
    }
}
