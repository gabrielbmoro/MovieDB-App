package com.gabrielbmoro.moviedb.core.ui.navigation

import cafe.adriel.voyager.core.screen.Screen
import com.gabrielbmoro.moviedb.domain.entities.Movie

interface MovieDBNavDestinations {
    fun searchScreen(): Screen
    fun wishListScreen(): Screen
    fun detailsScreen(movie: Movie): Screen
    fun moviesScreen(): Screen
}