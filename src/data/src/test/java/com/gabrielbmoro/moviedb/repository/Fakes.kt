package com.gabrielbmoro.moviedb.repository

import com.gabrielbmoro.moviedb.domain.entities.Movie
import com.gabrielbmoro.moviedb.domain.entities.VideoStream
import com.gabrielbmoro.moviedb.repository.datasources.retrofit.responses.MovieResponse
import com.gabrielbmoro.moviedb.repository.datasources.retrofit.responses.VideoStreamsResponse
import com.gabrielbmoro.moviedb.repository.datasources.retrofit.responses.VideoStreamsResponseItem
import com.gabrielbmoro.moviedb.repository.datasources.room.dto.FavoriteMovieDTO

val movieAndFavoriteMovieDTO = Pair(
    Movie(
        id = 12L,
        overview = "overview",
        title = "title",
        isFavorite = true,
        votesAverage = 12f,
        releaseDate = "10/02/1990",
        backdropImageUrl = "backgropImageUrl",
        language = "language",
        popularity = 2f,
        posterImageUrl = "posterImageUrl"
    ),
    FavoriteMovieDTO(
        id = null,
        overview = "overview",
        title = "title",
        votesAverage = 12f,
        releaseDate = "10/02/1990",
        backdropImageUrl = "backgropImageUrl",
        language = "language",
        popularity = 2f,
        posterImageUrl = "posterImageUrl",
        movieId = 12L
    )
)

val movieAndMovieResponse = Pair(
    Movie(
        id = 12L,
        overview = "overview",
        title = "title",
        isFavorite = false,
        votesAverage = 12f,
        releaseDate = "10/02/1990",
        backdropImageUrl = BIG_SIZE_IMAGE_ADDRESS.plus("backdropPath"),
        language = "language",
        popularity = 2f,
        posterImageUrl = SMALL_SIZE_IMAGE_ADDRESS.plus("posterPath")
    ),
    MovieResponse(
        id = 12L,
        overview = "overview",
        title = "title",
        votesAverage = 12f,
        releaseDate = "10/02/1990",
        popularity = 2f,
        isAdult = null,
        backdropPath = "backdropPath",
        posterPath = "posterPath",
        originalLanguage = "language",
        originalTitle = "originalTitle",
        isVideo = null,
        votes = null
    )
)

val videoStreamAndVideoStreamResponse = Pair(
    listOf(
        VideoStream(
            id = "12",
            key = "key",
            name = "name",
            official = true,
            site = "site",
            size = 12,
            type = "type"
        ),
        VideoStream(
            id = "13",
            key = "key1",
            name = "name1",
            official = true,
            site = "site1",
            size = 13,
            type = "type1"
        )
    ),
    VideoStreamsResponse(
        results = listOf(
            VideoStreamsResponseItem(
                id = "12",
                key = "key",
                name = "name",
                official = true,
                site = "site",
                size = 12,
                type = "type"
            ),
            VideoStreamsResponseItem(
                id = "13",
                key = "key1",
                name = "name1",
                official = true,
                site = "site1",
                size = 13,
                type = "type1"
            ),
        )
    )
)