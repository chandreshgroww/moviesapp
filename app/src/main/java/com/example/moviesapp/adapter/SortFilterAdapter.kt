package com.example.moviesapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.moviesapp.databinding.SortFilterDialogItemBinding
import com.example.moviesapp.models.SortFilter

class SortFilterAdapter(private val sortFilterClickListener: SortFilterClickListener) :
    ListAdapter<SortFilter, SortFilterAdapter.ViewHolder>(SortFilterDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position), sortFilterClickListener)
    }

    class ViewHolder private constructor(private val binding: SortFilterDialogItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: SortFilter, sortFilterClickListener: SortFilterClickListener) {
            binding.sortFilter = item
            binding.clickListener = sortFilterClickListener
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = SortFilterDialogItemBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }
        }
    }

}

class SortFilterDiffCallback : DiffUtil.ItemCallback<SortFilter>() {
    override fun areItemsTheSame(oldItem: SortFilter, newItem: SortFilter): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: SortFilter, newItem: SortFilter): Boolean {
        return oldItem.id == newItem.id && oldItem.name == newItem.name
    }
}

class SortFilterClickListener(val clickListener: (sortFilter: SortFilter) -> Unit) {
    fun onClick(sortFilter: SortFilter) = clickListener(sortFilter)
}