package com.gabrielbmoro.programmingchallenge.domain.model

sealed class MovieListType(val value: Int) {
    object TopRated : MovieListType(TOP_RATED_MOVIES_VALUE)
    object Favorite : MovieListType(FAVORITE_MOVIES_VALUE)
    object Popular : MovieListType(POPULAR_MOVIES_VALUE)
}

const val TOP_RATED_MOVIES_VALUE = 1
const val FAVORITE_MOVIES_VALUE = 2
const val POPULAR_MOVIES_VALUE = 3

fun Int.convertToMovieListType(): MovieListType? {
    return when (this) {
        TOP_RATED_MOVIES_VALUE -> MovieListType.TopRated
        FAVORITE_MOVIES_VALUE -> MovieListType.Favorite
        POPULAR_MOVIES_VALUE -> MovieListType.Popular
        else -> null
    }
}