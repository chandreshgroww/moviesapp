package com.example.moviesapp.adapter

import android.util.Log
import android.widget.ImageView
import androidx.core.net.toUri
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.moviesapp.models.Movie

private const val imagePreUrl: String = "https://image.tmdb.org/t/p/w500/"

private const val TAG = "BindingAdapters"

@BindingAdapter("bindImage")
fun bindImage(imgView: ImageView, imgUrl: String?) {
    imgUrl?.let {
//        val imageUrl = imagePreUrl + imgUrl
        Log.d(TAG, "bindImage: $imgUrl")
        val imgUri = imgUrl.toUri().buildUpon().scheme("https").build()
        Glide.with(imgView.context)
            .load(imgUri)
            .into(imgView)
    }
}

@BindingAdapter("bindMovieVertical")
fun bindMovieVertical(recyclerView: RecyclerView, data: List<Movie>?) {
    val adapter = recyclerView.adapter as MovieVerticalAdapter
    adapter.submitList(data)
}