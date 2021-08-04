package com.gabrielbmoro.programmingchallenge.repository.entities

data class Page(
    val movies: List<Movie>,
    val pageNumber: Int,
    val totalPages: Int
)