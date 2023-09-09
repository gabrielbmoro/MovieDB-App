package com.gabrielbmoro.moviedb.repository.model

enum class MovieListType(val value: Int) {
    TOP_RATED(TOP_RATED_MOVIES_VALUE),
    FAVORITE(FAVORITE_MOVIES_VALUE),
    POPULAR(POPULAR_MOVIES_VALUE),
    UPCOMING(UPCOMING_MOVIES_VALUE);
}

const val TOP_RATED_MOVIES_VALUE = 1
const val FAVORITE_MOVIES_VALUE = 2
const val POPULAR_MOVIES_VALUE = 3
const val UPCOMING_MOVIES_VALUE = 4

fun Int.convertToMovieListType(): MovieListType? {
    return MovieListType.values().firstOrNull { it.value == this }
}