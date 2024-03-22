package com.gabrielbmoro.moviedb.navigation

import cafe.adriel.voyager.core.screen.Screen
import com.gabrielbmoro.moviedb.core.ui.navigation.MovieDBNavDestinations
import com.gabrielbmoro.moviedb.details.ui.screens.details.DetailsScreen
import com.gabrielbmoro.moviedb.domain.entities.Movie
import com.gabrielbmoro.moviedb.movies.ui.screens.movies.MoviesScreen
import com.gabrielbmoro.moviedb.search.ui.screens.search.SearchScreen
import com.gabrielbmoro.moviedb.wishlist.ui.screens.wishlist.WishlistScreen

class MovieDBNavDestinationsImpl : MovieDBNavDestinations {
    override fun searchScreen(): Screen {
        return SearchScreen()
    }

    override fun wishListScreen(): Screen {
        return WishlistScreen()
    }

    override fun detailsScreen(movie: Movie): Screen {
        return DetailsScreen(movie)
    }

    override fun moviesScreen(): Screen {
        return MoviesScreen()
    }
}