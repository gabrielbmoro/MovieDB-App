package com.gabrielbmoro.moviedb.domain.entities

data class DataOrException<T, E : Exception>(
    val data: T? = null,
    val exception: E? = null
)