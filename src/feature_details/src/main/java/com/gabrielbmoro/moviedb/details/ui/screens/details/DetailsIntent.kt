package com.gabrielbmoro.moviedb.details.ui.screens.details

sealed class DetailsIntent {
    data object HideVideo : DetailsIntent()

    data object FavoriteMovie : DetailsIntent()
}