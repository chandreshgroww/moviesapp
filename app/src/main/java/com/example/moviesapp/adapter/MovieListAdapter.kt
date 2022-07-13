package com.example.moviesapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.example.moviesapp.databinding.MovieHorizontalCardBinding
import com.example.moviesapp.databinding.MovieVerticalCardBinding
import com.example.moviesapp.models.Movie

class MovieListAdapter(private val type: Int, private val movieClickListener: MovieClickListener) :
    ListAdapter<Movie, MovieListAdapter.ViewHolder>(MovieDiffCallback()) {
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        when(type) {
            0 -> holder.bindVertical(getItem(position), movieClickListener)
            else -> holder.bindHorizontal(getItem(position), movieClickListener)
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MovieListAdapter.ViewHolder {
        return when(type) {
            0 -> ViewHolder.fromVertical(parent)
            else -> ViewHolder.fromHorizontal(parent)
        }
    }

    class ViewHolder private constructor(private val binding: ViewBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bindVertical(item: Movie, movieClickListener: MovieClickListener) {
            (binding as MovieVerticalCardBinding).apply {
                movie = item
                clickListener = movieClickListener
                executePendingBindings()
            }
        }

        fun bindHorizontal(item: Movie?, movieClickListener: MovieClickListener) {
            (binding as MovieHorizontalCardBinding).apply {
                movie = item
                clickListener = movieClickListener
                executePendingBindings()
            }
        }

        companion object {
            fun fromVertical(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = MovieVerticalCardBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }

            fun fromHorizontal(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = MovieHorizontalCardBinding.inflate(layoutInflater, parent, false)
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


class MovieClickListener(val clickListener: (movie: Movie) -> Unit) {
    fun onClick(movie: Movie) = clickListener(movie)
}