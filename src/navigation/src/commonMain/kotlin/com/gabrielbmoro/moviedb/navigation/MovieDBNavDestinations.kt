package com.gabrielbmoro.moviedb.navigation

import cafe.adriel.voyager.core.screen.Screen
import com.gabrielbmoro.moviedb.domain.entities.Movie

interface MovieDBNavDestinations {
    fun searchScreen(): Screen
    fun wishListScreen(): Screen
    fun detailsScreen(movie: Movie): Screen
    fun moviesScreen(): Screen
}

class MovieDBNavDestinationsImpl: MovieDBNavDestinations {
    override fun searchScreen(): Screen {
        throw Throwable("Not implemented yet")
    }

    override fun wishListScreen(): Screen {
        throw Throwable("Not implemented yet")
    }

    override fun detailsScreen(movie: Movie): Screen {
        throw Throwable("Not implemented yet")
    }

    override fun moviesScreen(): Screen {
        throw Throwable("Not implemented yet")
    }
}