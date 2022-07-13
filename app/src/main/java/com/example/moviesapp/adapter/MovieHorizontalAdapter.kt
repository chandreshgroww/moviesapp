package com.example.moviesapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.moviesapp.databinding.MovieHorizontalCardBinding
import com.example.moviesapp.models.Movie

class MovieHorizontalAdapter(private val movieHorizontalListener: MovieHorizontalListener) :
    ListAdapter<Movie, MovieHorizontalAdapter.ViewHolder>(MovieDiffCallback()) {

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item, movieHorizontalListener)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MovieHorizontalAdapter.ViewHolder {
        return ViewHolder.from(parent)
    }

    class ViewHolder private constructor(private val binding: MovieHorizontalCardBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Movie, movieHorizontalListener: MovieHorizontalListener) {
            binding.movie = item
            binding.clickListener = movieHorizontalListener
            binding.executePendingBindings()
        }
        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = MovieHorizontalCardBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }
        }

    }
}

class MovieHorizontalListener(val clickListener: (movie: Movie) -> Unit) {
    fun onClick(movie: Movie) = clickListener(movie)
}