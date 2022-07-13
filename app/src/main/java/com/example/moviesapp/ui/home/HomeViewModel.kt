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

class HomeViewModel @Inject constructor(private val repository: MovieRepository, private val remoteDataSource: RemoteDataSource) : ViewModel() {

    val popularMovieList = repository.popularMovieList

//    fun voteCountMovieList(): Flow<PagingData<Movie>> { return Pager(
//        config = PagingConfig(pageSize = 2, enablePlaceholders = true, maxSize = 100),
//        pagingSourceFactory = { MoviePagingSource(remoteDataSource.apiService) }
//    ).flow.cachedIn(viewModelScope)
//    }

    private val _networkStatus = MutableLiveData<NetworkResult>()
    val networkStatus: LiveData<NetworkResult>
        get() = _networkStatus


}


