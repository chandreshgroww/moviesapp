package com.example.moviesapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.moviesapp.databinding.MovieVerticalCardBinding
import com.example.moviesapp.models.Movie

class MovieVerticalAdapter(private val movieVerticalListener: MovieVerticalListener) :
    ListAdapter<Movie, MovieVerticalAdapter.ViewHolder>(MovieDiffCallback()) {
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item, movieVerticalListener)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MovieVerticalAdapter.ViewHolder {
        return ViewHolder.from(parent)
    }

    class ViewHolder private constructor(private val binding: MovieVerticalCardBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Movie, movieVerticalListener: MovieVerticalListener) {
            binding.movie = item
            binding.executePendingBindings()
        }
        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = MovieVerticalCardBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }
        }

    }
}

class MovieDiffCallback: DiffUtil.ItemCallback<Movie>() {
    override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
        return oldItem == newItem
    }
}


class MovieVerticalListener(val clickListener: (movie: Movie) -> Unit) {
    fun onClick(movie: Movie) = clickListener(movie)
}