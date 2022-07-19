package com.example.moviesapp.repository

import com.example.moviesapp.models.DiscoverResult
import com.example.moviesapp.models.MovieDetail
import com.example.moviesapp.models.SortFilter
import com.example.moviesapp.util.Result
import com.example.moviesapp.util.SortBy

interface DataSource {
    suspend fun fetchMoviesList(
        sortBy: SortBy,
        withGenres: String? = null,
        page: Int = 1
    ): Result<DiscoverResult>

    suspend fun fetchMovieDetail(movieId: Int): Result<MovieDetail>

    suspend fun fetchAllGenreList(): Result<List<SortFilter>>
}