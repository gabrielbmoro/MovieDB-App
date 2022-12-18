package com.gabrielbmoro.programmingchallenge.domain.model

data class Page(
    val movies: List<Movie>,
    val pageNumber: Int,
    val totalPages: Int
)