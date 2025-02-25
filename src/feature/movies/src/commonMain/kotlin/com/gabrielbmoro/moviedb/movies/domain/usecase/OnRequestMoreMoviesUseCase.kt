package com.gabrielbmoro.moviedb.movies.domain.usecase

interface OnRequestMoreMoviesUseCase {
    suspend operator fun invoke()
}
