package com.gabrielbmoro.moviedb.movies.domain.usecase

interface UpdateStateBasedOnNextPageUseCase {
    suspend operator fun invoke(page: Int)
}
