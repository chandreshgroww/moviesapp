package com.example.moviesapp.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.moviesapp.models.DiscoverResult
import com.example.moviesapp.models.Movie
import com.example.moviesapp.network.ApiService
import java.lang.Exception

private const val TAG = "MovieRepository"

class MovieRepository(private val apiService: ApiService) {

    private val _popularMovieList = MutableLiveData<List<Movie>>()
    val popularMovieList: LiveData<List<Movie>>
        get() = _popularMovieList

    private val _latestMovieList = MutableLiveData<List<Movie>>()
    val latestMovieList: LiveData<List<Movie>>
        get() = _latestMovieList

    suspend fun getPopularMoviesList() {
        val resultMovies = apiService.getPopularMoviesAsync()
        try {
            _popularMovieList.postValue(resultMovies.body()?.results)
        } catch (e: Exception) {
            Log.i(TAG, "getMoviesList: $e")
        }
    }

    suspend fun getLatestMoviesList() {
        val resultMovies = apiService.getLatestMoviesAsync("vote_count.desc")
        try {
            _latestMovieList.postValue(resultMovies.body()?.results)
        } catch (e: Exception) {
            Log.i(TAG, "getMoviesList: $e")
        }
    }

}