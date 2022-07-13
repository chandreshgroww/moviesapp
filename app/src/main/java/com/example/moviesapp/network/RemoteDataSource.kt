package com.example.moviesapp.network

import com.example.moviesapp.models.DiscoverResult
import com.example.moviesapp.models.Movie
import com.example.moviesapp.util.BaseDataSource
import com.example.moviesapp.util.Result
import com.example.moviesapp.util.SortBy
import retrofit2.Response
import javax.inject.Inject

class RemoteDataSource @Inject constructor(val apiService: ApiService): BaseDataSource() {

    suspend fun fetchPopularList(sortBy: SortBy): Result<DiscoverResult> =
        getResult { apiService.getMovieListAsync(sortBy.notation, 1) }

}