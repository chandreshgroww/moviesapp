package com.example.moviesapp.network

import com.example.moviesapp.models.DiscoverResult
import com.example.moviesapp.util.Result
import com.example.moviesapp.util.SortBy
import javax.inject.Inject

class RemoteDataSource @Inject constructor(private val apiService: ApiService) : BaseDataSource() {

    suspend fun fetchMoviesList(
        sortBy: SortBy,
        withGenres: String? = null,
        page: Int = 1
    ): Result<DiscoverResult> = getResult {
        apiService.getMovieListAsync(sortBy.notation, withGenres, page)
    }

    suspend fun fetchMovieDetail(movieId: Int) = getResult {
        apiService.getMovieDetails(movieId)
    }

}