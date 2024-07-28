package com.gabrielbmoro.moviedb.platform.navigation

enum class Screen(
    val route: String,
    val firstSegment: String?,
) {
    Movies(
        route = "movies",
        firstSegment = null
    ),
    Search(
        route = "Search",
        firstSegment = "search"
    ),
    Details(
        route = "details",
        firstSegment = "movie"
    ),
    Wishlist(
        route = "wishlist",
        firstSegment = "favorite"
    )
}

internal const val DETAILS_MOVIE_ID_ARGUMENT_KEY = "movieId"
const val SEARCH_QUERY_ARGUMENT_KEY = "query"
