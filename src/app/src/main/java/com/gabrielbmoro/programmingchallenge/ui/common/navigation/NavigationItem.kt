package com.gabrielbmoro.programmingchallenge.ui.common.navigation

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.gabrielbmoro.programmingchallenge.R
import com.gabrielbmoro.programmingchallenge.domain.model.Movie

sealed class NavigationItem(
    open val route: String
) {
    open class NavigationItemTab(
        @DrawableRes val icon: Int,
        @StringRes val title: Int,
        override val route: String
    ) : NavigationItem(route = route)

    object TopRatedMovies : NavigationItemTab(
        icon = R.drawable.ic_photo_album,
        title = R.string.top_rated_movies_title,
        route = ScreenRoutesBuilder.TOP_RATED_MOVIES_ROUTE
    )

    object PopularMovies : NavigationItemTab(
        icon = R.drawable.ic_theaters,
        title = R.string.popular_movies_title,
        route = ScreenRoutesBuilder.POPULAR_MOVIES_ROUTE
    )

    object FavoriteMovies : NavigationItemTab(
        icon = R.drawable.ic_stars,
        title = R.string.favorite_movies_title,
        route = ScreenRoutesBuilder.FAVORITE_MOVIES_ROUTE
    )

    data class DetailsScreen(
        private val movie: Movie
    ) : NavigationItem(
        ScreenRoutesBuilder.detailedMovieRoute(movie)
    )
}