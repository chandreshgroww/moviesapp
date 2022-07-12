package com.example.moviesapp.network

import com.example.moviesapp.models.DiscoverResult
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.Deferred
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.*

private const val BASE_URL = "https://api.themoviedb.org/"

private const val apiKey = "a3674b222a9813d0520b204500146b8a"

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

private val retrofitMovie = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .addCallAdapterFactory(CoroutineCallAdapterFactory())
    .baseUrl(BASE_URL)
    .build()

interface ApiService {
    @GET("/3/discover/movie")
    fun getLatestMoviesAsync(
        @Query("sort_by") sort_by: String,
        @Query("api_key") api_key: String = apiKey
    ): Deferred<DiscoverResult>

    @GET("/3/movie/popular")
    fun getPopularMoviesAsync(
        @Query("api_key") api_key: String = apiKey
    ): Deferred<DiscoverResult>
}

object MovieApi {
    val retrofitService: ApiService by lazy {
        retrofitMovie.create(ApiService::class.java)
    }
}
