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

    private val _popularMovieList = MutableLiveData<List<Movie>>()
    val popularMovieList: LiveData<List<Movie>>
        get() = _popularMovieList

    private val _latestMovieList = MutableLiveData<List<Movie>>()
    val latestMovieList: LiveData<List<Movie>>
        get() = _latestMovieList

    init {
        Log.d(TAG, "Initialized HomeViewModel")
        getPopularMoviesList()
        getLatestMoviesList()
    }

    private fun getPopularMoviesList() {
        viewModelScope.launch {
            val getMoviesDeferred = MovieApi.retrofitService.getPopularMoviesAsync()
            try {
                val resultMovies = getMoviesDeferred.await()
                _popularMovieList.value = resultMovies.results
            } catch (e: Exception) {
                Log.i(TAG, "getMoviesList: $e")
                _popularMovieList.value = listOf()
            }
        }
    }

    private fun getLatestMoviesList() {
        viewModelScope.launch {
            val getMoviesDeferred = MovieApi.retrofitService.getLatestMoviesAsync("vote_count.desc")
            try {
                val resultMovies = getMoviesDeferred.await()
                _latestMovieList.value = resultMovies.results
                Log.d(TAG, "getLatestMoviesList: ${resultMovies.total_results}")
            } catch (e: Exception) {
                Log.i(TAG, "getMoviesList: $e")
                _latestMovieList.value = listOf()
            }
        }
    }

}