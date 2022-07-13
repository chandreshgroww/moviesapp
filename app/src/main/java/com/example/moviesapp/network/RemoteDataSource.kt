package com.example.moviesapp.network

import com.example.moviesapp.models.DiscoverResult
import com.example.moviesapp.models.Movie
import com.example.moviesapp.util.BaseDataSource
import com.example.moviesapp.util.Result
import retrofit2.Response
import javax.inject.Inject

class RemoteDataSource @Inject constructor(val apiService: ApiService): BaseDataSource() {

    suspend fun fetchPopularList(): Result<DiscoverResult> =
        getResult { apiService.getMovieListAsync("popularity.desc", 1) }

}