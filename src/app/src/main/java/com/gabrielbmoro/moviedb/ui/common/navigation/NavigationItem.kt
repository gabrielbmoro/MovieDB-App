package com.gabrielbmoro.moviedb.ui.common.navigation

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.gabrielbmoro.moviedb.R
import com.gabrielbmoro.moviedb.domain.model.Movie

sealed class NavigationItem(
    open val route: String
) {
    open class NavigationItemTab(
        @DrawableRes val icon: Int,
        @StringRes val title: Int,
        override val route: String
    ) : NavigationItem(route = route)

    object Movies : NavigationItemTab(
        icon = R.drawable.ic_theaters,
        title = R.string.movies,
        route = ScreenRoutesBuilder.MOVIES_ROUTE
    )

    object FavoriteMovies : NavigationItemTab(
        icon = R.drawable.ic_stars,
        title = R.string.favorite,
        route = ScreenRoutesBuilder.FAVORITE_MOVIES_ROUTE
    )

    data class DetailsScreen(
        private val movie: Movie
    ) : NavigationItem(
        ScreenRoutesBuilder.detailedMovieRoute(movie)
    )
}