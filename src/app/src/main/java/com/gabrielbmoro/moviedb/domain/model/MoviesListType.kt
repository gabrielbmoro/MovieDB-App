package com.gabrielbmoro.moviedb.domain.model

enum class MovieListType(val value: Int) {
    TOP_RATED(TOP_RATED_MOVIES_VALUE),
    FAVORITE(FAVORITE_MOVIES_VALUE),
    POPULAR(POPULAR_MOVIES_VALUE);
}

const val TOP_RATED_MOVIES_VALUE = 1
const val FAVORITE_MOVIES_VALUE = 2
const val POPULAR_MOVIES_VALUE = 3

fun Int.convertToMovieListType(): MovieListType? {
    return MovieListType.values().firstOrNull { it.value == this }
}