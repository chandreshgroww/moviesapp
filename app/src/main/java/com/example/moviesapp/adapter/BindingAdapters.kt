package com.example.moviesapp.adapter

import android.graphics.Color
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.TextView
import androidx.core.net.toUri
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.example.moviesapp.R
import com.example.moviesapp.models.MovieDetail
import com.example.moviesapp.util.Result

@BindingAdapter("bindImage")
fun bindImage(imgView: ImageView, imgUrl: String?) {
    imgUrl?.let {
        val imgUri = imgUrl.toUri().buildUpon().scheme("https").build()
        Glide.with(imgView.context)
            .load(imgUri)
            .into(imgView)
    }
}

@BindingAdapter("hideOnLoading")
fun ViewGroup.hideOnLoading(result: Result<*>?) {
    visibility = if(result == null) {
        View.GONE
    }
    else {
        if (result.status == Result.Status.SUCCESS)
            View.VISIBLE
        else
            View.GONE
    }
}

@BindingAdapter("showOnLoading")
fun ProgressBar.showOnLoading(result: Result<*>?) {
    visibility = if(result == null) {
        View.VISIBLE
    }
    else {
        if (result.status == Result.Status.LOADING)
            View.VISIBLE
        else
            View.GONE
    }
}

@BindingAdapter("showOnError")
fun TextView.showError(result: Result<*>?) {
    visibility = if (result?.status == Result.Status.ERROR) {
        text = result.message
        View.VISIBLE
    }
    else
        View.GONE
}

@BindingAdapter("addChipTextView")
fun addChipTextView(linearLayout: LinearLayout, movie: Result<MovieDetail>?) {
    linearLayout.removeAllViews()
    if(movie?.status == Result.Status.SUCCESS && movie.data != null) {
        movie.data.genres.let {
            it?.forEach { tag ->
                if (tag.name.isNotEmpty()) {
                    val textView = TextView(linearLayout.context)
                    textView.text = tag.name
                    val params = LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.WRAP_CONTENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT
                    )
                    params.setMargins(0, 6, 15, 6)
                    textView.layoutParams = params
                    textView.textSize = 10f
                    textView.isClickable = true
                    textView.setBackgroundResource(R.drawable.chip_bg)
                    textView.setTextColor(Color.parseColor("#3082F2"))
                    linearLayout.addView(textView)
                }
            }
        }
    }
}