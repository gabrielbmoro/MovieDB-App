package com.gabrielbmoro.programmingchallenge.presentation.components.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.gabrielbmoro.programmingchallenge.databinding.ViewHolderMovieCardBinding
import com.gabrielbmoro.programmingchallenge.presentation.components.compose.MovieCard
import com.gabrielbmoro.programmingchallenge.presentation.components.compose.theme.MovieDBAppTheme
import com.gabrielbmoro.programmingchallenge.repository.entities.Movie

class MovieViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    private val binding: ViewHolderMovieCardBinding = ViewHolderMovieCardBinding.bind(view)

    fun bind(movie: Movie, selectedMovieCallback: ((Movie, View) -> Unit)) {

        binding.composeBase.setContent {
            MovieDBAppTheme {
                MovieCard(
                    imageUrl = movie.posterPath,
                    title = movie.title ?: "",
                    releaseDate = movie.releaseDate ?: "",
                    votes = movie.votesAverage ?: 0f,
                    onClick = {
                        selectedMovieCallback.invoke(movie, binding.composeBase)
                    }
                )
            }
        }
    }
}