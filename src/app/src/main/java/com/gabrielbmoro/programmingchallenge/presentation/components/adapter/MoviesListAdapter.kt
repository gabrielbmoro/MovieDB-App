package com.gabrielbmoro.programmingchallenge.presentation.components.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.gabrielbmoro.programmingchallenge.R
import com.gabrielbmoro.programmingchallenge.domain.model.Movie

class MoviesListAdapter : ListAdapter<Movie, MovieViewHolder>(DiffCallback()) {

    private var elements: List<Movie> = emptyList()
        set(value) {
            field = value
            submitList(field)
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        return MovieViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.view_holder_movie_card, parent, false)
        )
    }

    override fun getItemCount() = elements.size

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        try {
            elements[position]
        } catch (indexOutOfBounds: IndexOutOfBoundsException) {
            null
        }?.let { data ->
            holder.bind(data)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return VIEW_TYPE
    }

    fun refresh(movies: List<Movie>) {
        elements = movies
    }

    private class DiffCallback : DiffUtil.ItemCallback<Movie>() {
        override fun areItemsTheSame(oldItem: Movie, newItem: Movie) = (oldItem.id == newItem.id)

        override fun areContentsTheSame(oldItem: Movie, newItem: Movie) = (
                oldItem.id == newItem.id
                        && oldItem.backdropPath == newItem.backdropPath
                        && oldItem.originalLanguage == newItem.originalLanguage
                        && oldItem.originalTitle == newItem.originalTitle
                        && oldItem.overview == newItem.overview
                        && oldItem.title == newItem.title
                )
    }

    companion object {
        const val VIEW_TYPE = -123
    }
}