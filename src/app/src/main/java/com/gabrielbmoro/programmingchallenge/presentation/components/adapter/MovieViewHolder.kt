package com.gabrielbmoro.programmingchallenge.presentation.components.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.gabrielbmoro.programmingchallenge.databinding.ViewHolderMovieCardBinding
import com.gabrielbmoro.programmingchallenge.presentation.components.compose.FiveStars
import com.gabrielbmoro.programmingchallenge.presentation.components.compose.MovieCardInformation
import com.gabrielbmoro.programmingchallenge.presentation.components.compose.theme.MovieDBAppTheme
import com.gabrielbmoro.programmingchallenge.repository.entities.Movie
import com.gabrielbmoro.programmingchallenge.presentation.util.setImagePath

class MovieViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {

    private val binding: ViewHolderMovieCardBinding = ViewHolderMovieCardBinding.bind(view)

    fun bind(movie: Movie, selectedMovieCallback: ((Movie, View) -> Unit)) {
        movie.posterPath?.let { imagePath ->
            binding.viewHolderMovieCardPoster.setImagePath(imagePath)
        }

        val votesAvg = movie.votesAverage ?: 0f
        binding.composeFiveStars.setContent {
            FiveStars(votes = votesAvg)
        }

        binding.composeMovieDetailedCard.setContent {
            MovieDBAppTheme {
                MovieCardInformation(
                    title = movie.title ?: "",
                    releaseDate = movie.releaseDate ?: ""
                )
            }
        }

        view.setOnClickListener {
            selectedMovieCallback.invoke(movie, binding.viewHolderMovieCardPoster)
        }
    }
}