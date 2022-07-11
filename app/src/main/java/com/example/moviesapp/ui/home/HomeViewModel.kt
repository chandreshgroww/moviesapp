package com.example.moviesapp.ui.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moviesapp.models.Movie
import com.example.moviesapp.network.MovieApi
import kotlinx.coroutines.launch
import java.lang.Exception

private const val TAG = "HomeViewModel"

class HomeViewModel: ViewModel() {

    private val _moviesList = MutableLiveData<List<Movie>>()
    val moviesList: LiveData<List<Movie>>
        get() = _moviesList

    init {
        Log.d(TAG, "Initialized HomeViewModel")
        getMoviesList()
    }

    private fun getMoviesList() {
        viewModelScope.launch {
            val getMoviesDeferred = MovieApi.retrofitService.getLatestMoviesAsync()
            try {
                val resultMovies = getMoviesDeferred.await()
                _moviesList.value = resultMovies.results
                Log.d(TAG, "getMoviesList: ${resultMovies.total_results}")
            } catch (e: Exception) {
                Log.i(TAG, "getMoviesList: $e")
                _moviesList.value = listOf()
            }
        }
    }

}