package com.gabrielbmoro.programmingchallenge.presentation.favoriteMovieList

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import com.gabrielbmoro.programmingchallenge.R
import com.gabrielbmoro.programmingchallenge.domain.model.Movie
import com.gabrielbmoro.programmingchallenge.presentation.components.adapter.MovieViewHolder

class FavoriteMoviesListAdapter : PagedListAdapter<Movie, MovieViewHolder>(DIFF_CALLBACK) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        return MovieViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.view_holder_movie_card, parent, false))
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        getItem(position)?.let {
            holder.bind(movie = it)
        }
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Movie>() {
            // The ID property identifies when items are the same.
            override fun areItemsTheSame(oldItem: Movie, newItem: Movie) = (oldItem.id == newItem.id)

            // If you use the "==" operator, make sure that the object implements
            // .equals(). Alternatively, write custom data comparison logic here.
            override fun areContentsTheSame(oldItem: Movie, newItem: Movie) = (
                    oldItem.id == newItem.id
                            && oldItem.backdropPath == newItem.backdropPath
                            && oldItem.originalLanguage == newItem.originalLanguage
                            && oldItem.originalTitle == newItem.originalTitle
                            && oldItem.overview == newItem.overview
                            && oldItem.title == newItem.title
                    )
        }

    }
}