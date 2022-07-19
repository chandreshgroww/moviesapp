package com.example.moviesapp.network

import com.example.moviesapp.data.TestData
import com.example.moviesapp.models.*
import com.example.moviesapp.util.Result
import com.example.moviesapp.util.SortBy
import org.junit.Assert.*

import org.junit.Test

class RemoteDataSourceTest: BaseDataSource(), IRemoteDataSource {

    val results = listOf(
        Movie()
    )

    override suspend fun fetchMoviesList(
        sortBy: SortBy,
        withGenres: String?,
        page: Int
    ): Result<DiscoverResult> {
        return Result.success(DiscoverResult(1, results, 5, 100))
    }

    override suspend fun fetchMovieDetail(movieId: Int): Result<MovieDetail> {
        return Result.success(MovieDetail())
    }

    override suspend fun fetchAllGenreList(): Result<GenreResult> {
        return Result.success(GenreResult(TestData.sortFilterList))
    }
}