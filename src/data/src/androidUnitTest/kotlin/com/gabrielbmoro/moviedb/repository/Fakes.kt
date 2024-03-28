package com.gabrielbmoro.moviedb.repository

import com.gabrielbmoro.moviedb.data.repository.BIG_SIZE_IMAGE_ADDRESS
import com.gabrielbmoro.moviedb.data.repository.SMALL_SIZE_IMAGE_ADDRESS
import com.gabrielbmoro.moviedb.data.repository.datasources.ktor.responses.MovieResponse
import com.gabrielbmoro.moviedb.data.repository.datasources.ktor.responses.VideoStreamsResponse
import com.gabrielbmoro.moviedb.data.repository.datasources.ktor.responses.VideoStreamsResponseItem
import com.gabrielbmoro.moviedb.data.repository.datasources.room.dto.FavoriteMovieDTO
import com.gabrielbmoro.moviedb.domain.entities.Movie
import com.gabrielbmoro.moviedb.domain.entities.VideoStream

val movieAndFavoriteMovieDTO =
    Pair(
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
            posterImageUrl = "posterImageUrl",
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
            movieId = 12L,
        ),
    )

val movieAndMovieResponse =
    Pair(
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
            posterImageUrl = SMALL_SIZE_IMAGE_ADDRESS.plus("posterPath"),
        ),
        MovieResponse(
            id = 12L,
            overview = "overview",
            title = "title",
            vote_average = 12f,
            release_date = "10/02/1990",
            popularity = 2f,
            adult = null,
            backdrop_path = "backdropPath",
            poster_path = "posterPath",
            original_language = "language",
            original_title = "originalTitle",
            video = null,
            vote_count = null,
        ),
    )

val videoStreamAndVideoStreamResponse =
    Pair(
        listOf(
            VideoStream(
                id = "12",
                key = "key",
                name = "name",
                official = true,
                site = "site",
                size = 12,
                type = "type",
            ),
            VideoStream(
                id = "13",
                key = "key1",
                name = "name1",
                official = true,
                site = "site1",
                size = 13,
                type = "type1",
            ),
        ),
        VideoStreamsResponse(
            results =
                listOf(
                    VideoStreamsResponseItem(
                        id = "12",
                        key = "key",
                        name = "name",
                        official = true,
                        site = "site",
                        size = 12,
                        type = "type",
                    ),
                    VideoStreamsResponseItem(
                        id = "13",
                        key = "key1",
                        name = "name1",
                        official = true,
                        site = "site1",
                        size = 13,
                        type = "type1",
                    ),
                ),
        ),
    )
