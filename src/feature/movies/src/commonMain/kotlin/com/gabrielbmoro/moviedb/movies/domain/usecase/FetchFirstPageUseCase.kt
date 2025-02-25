package com.gabrielbmoro.moviedb.movies.domain.usecase

interface FetchFirstPageUseCase {
    suspend operator fun invoke()
}
