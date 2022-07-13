package com.example.moviesapp.ui.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.*
import com.example.moviesapp.models.Movie
import com.example.moviesapp.network.RemoteDataSource
import com.example.moviesapp.paging.MoviePagingSource
import com.example.moviesapp.repository.MovieRepository
import com.example.moviesapp.util.NetworkResult
import com.example.moviesapp.util.Result
import com.example.moviesapp.util.SortBy
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

private const val TAG = "HomeViewModel"

class HomeViewModel @Inject constructor(repository: MovieRepository) : ViewModel() {

    val popularMovieList = repository.popularMovieList

    val voteCountMovieList = repository.viewCountMovieList

}


