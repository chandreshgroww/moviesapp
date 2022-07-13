package com.example.moviesapp.network

import com.example.moviesapp.models.DiscoverResult
import com.example.moviesapp.util.BaseDataSource
import com.example.moviesapp.util.Result
import com.example.moviesapp.util.SortBy
import javax.inject.Inject

class RemoteDataSource @Inject constructor(val apiService: ApiService) : BaseDataSource() {

    suspend fun fetchMoviesList(sortBy: SortBy) = getResult {
        apiService.getMovieListAsync(sortBy.notation)
    }

    suspend fun fetchMovieDetail(movieId: Int) = getResult {
        apiService.getMovieDetails(movieId)
    }

}