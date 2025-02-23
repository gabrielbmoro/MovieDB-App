package com.gabrielbmoro.moviedb.movies.domain.usecase

interface ListenToPaginationUseCase {
    suspend operator fun invoke()
}
