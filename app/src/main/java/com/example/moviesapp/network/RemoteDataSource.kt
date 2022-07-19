package com.example.moviesapp.network

import com.example.moviesapp.models.*
import com.example.moviesapp.util.Result
import com.example.moviesapp.util.SortBy
import javax.inject.Inject

class RemoteDataSource @Inject constructor(private val apiService: ApiService) : BaseDataSource(),
    IRemoteDataSource {

     override suspend fun fetchMoviesList(
         sortBy: SortBy,
         withGenres: String?,
         page: Int
     ): Result<DiscoverResult> = getResult {
        apiService.getMovieListAsync(sortBy.notation, withGenres, page)
    }

     override suspend fun fetchMovieDetail(movieId: Int) = getResult {
        apiService.getMovieDetails(movieId)
    }

     override suspend fun fetchAllGenreList() = getResult {
        apiService.getAllGenreList()
    }

}

interface IRemoteDataSource {
    suspend fun fetchMoviesList(
        sortBy: SortBy,
        withGenres: String? = null,
        page: Int = 1
    ): Result<DiscoverResult>

    suspend fun fetchMovieDetail(movieId: Int): Result<MovieDetail>

    suspend fun fetchAllGenreList(): Result<GenreResult>
}