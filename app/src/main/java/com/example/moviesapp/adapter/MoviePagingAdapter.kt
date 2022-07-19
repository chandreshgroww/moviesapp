package com.example.moviesapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.moviesapp.databinding.MovieHorizontalCardBinding
import com.example.moviesapp.models.Movie

class MoviePagingAdapter(private val movieClickListener: MovieClickListener) :
    PagingDataAdapter<Movie, MoviePagingAdapter.ViewHolder>(MovieDiffCallback()) {

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item, movieClickListener)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        return ViewHolder.from(parent)
    }

    class ViewHolder private constructor(private val binding: MovieHorizontalCardBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Movie?, movieClickListener: MovieClickListener) {
            if(item != null)
                binding.movie = item
            binding.clickListener = movieClickListener
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