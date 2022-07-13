package com.example.moviesapp.network

import com.example.moviesapp.models.DiscoverResult
import com.example.moviesapp.models.MovieDetail
import com.example.moviesapp.util.Constants
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.*
import java.util.*

interface ApiService {

    @GET("/3/discover/movie")
    suspend fun getMovieListAsync(
        @Query("sort_by") sort_by: String,
        @Query("api_key") api_key: String = Constants.apiKey
    ): Response<DiscoverResult>

    @GET("/3/movie/{movieId}")
    suspend fun getMovieDetails(
        @Path("movieId") movieId: Long = -1,
        @Query("api_key") api_key: String = Constants.apiKey
    ): Response<MovieDetail>

}
