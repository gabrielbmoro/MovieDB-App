package com.gabrielbmoro.programmingchallenge.presentation.components.adapter

import android.app.Activity
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.gabrielbmoro.programmingchallenge.databinding.ViewHolderMovieCardBinding
import com.gabrielbmoro.programmingchallenge.repository.entities.Movie
import com.gabrielbmoro.programmingchallenge.presentation.detailedScreen.MovieDetailedActivity
import com.gabrielbmoro.programmingchallenge.presentation.util.setImagePath

class MovieViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {

    private val binding: ViewHolderMovieCardBinding = ViewHolderMovieCardBinding.bind(view)

    fun bind(movie: Movie) {
        movie.posterPath?.let { imagePath ->
            binding.viewHolderMovieCardPoster.setImagePath(imagePath)
        }
        binding.viewHolderMovieCardTitle.text = movie.title
        binding.viewHolderMovieCardReleaseDate.text = movie.releaseDate

        val votesAvg = movie.votesAverage ?: 0f
        binding.viewHolderMovieCardFiveStarsComponent.setVotesAvg(votesAvg)

        view.setOnClickListener {
            (view.context as? Activity)?.let { activity ->
                MovieDetailedActivity.startActivity(
                    activity,
                    movie,
                    binding.viewHolderMovieCardPoster
                )
            }
        }
    }
}