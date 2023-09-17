package com.gabrielbmoro.moviedb.repository.model

data class Page(
    val movies: List<Movie>,
    val pageNumber: Int,
    val totalPages: Int
) {
    companion object {
        fun mockPageWithWhiteDragonMovieOnly() = Page(
            listOf(
                Movie(
                    id = 12L,
                    2f,
                    "Dragão branco",
                    "https://dragaobranco.png",
                    "https://dragaobranco.png",
                    "Movie where Vandame shows how a good Karate fighter fights",
                    "2002-02-21",
                    language = "pt-br",
                    popularity = 2f,
                    isFavorite = false
                )
            ),
            1,
            1
        )
    }
}
