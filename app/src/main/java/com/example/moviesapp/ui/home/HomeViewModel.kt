package com.example.moviesapp.ui.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moviesapp.repository.MovieRepository
import com.example.moviesapp.util.NetworkResult
import com.example.moviesapp.util.SortBy
import kotlinx.coroutines.*

private const val TAG = "HomeViewModel"

class HomeViewModel(private val repository: MovieRepository) : ViewModel() {

    val popularMovieList = repository.popularMovieList

    val voteCountMovieList = repository.voteCountMovieList

    private val _networkStatus = MutableLiveData<NetworkResult>()
    val networkStatus: LiveData<NetworkResult>
        get() = _networkStatus

    init {
        fetchLatestData()
    }

    private fun fetchLatestData() {
        _networkStatus.value = NetworkResult.Loading
        viewModelScope.launch(Dispatchers.IO) {
            val voteCountResult = async { repository.fetchLatestMovies(SortBy.VoteCount) }
            val popularityResult = async { repository.fetchLatestMovies(SortBy.PopularityDesc) }

            if (voteCountResult.await() is NetworkResult.Error || popularityResult.await() is NetworkResult.Error) {
                _networkStatus.postValue(NetworkResult.Error("Error"))
            } else {
                _networkStatus.postValue(NetworkResult.Success(true))
            }
        }
    }

}


