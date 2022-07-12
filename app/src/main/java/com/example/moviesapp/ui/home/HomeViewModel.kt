package com.example.moviesapp.ui.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moviesapp.models.Movie
import com.example.moviesapp.network.MovieApi
import com.example.moviesapp.repository.MovieRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

private const val TAG = "HomeViewModel"

class HomeViewModel(private val repository: MovieRepository): ViewModel() {

    val popularMovieList: LiveData<List<Movie>>
        get() = repository.popularMovieList

    val latestMovieList: LiveData<List<Movie>>
        get() = repository.latestMovieList

    init {
        Log.d(TAG, "Initialized HomeViewModel")
        getPopularMoviesList()
        getLatestMoviesList()
    }

    private fun getLatestMoviesList() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.getLatestMoviesList()
        }
    }

    private fun getPopularMoviesList() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.getPopularMoviesList()
        }
    }

}


