package com.example.moviesapp.di

import com.example.moviesapp.network.ApiService
import com.example.moviesapp.util.Constants
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import io.mockk.mockk
import io.mockk.mockkClass
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
class TestNetworkModule {

    @Singleton
    @Provides
    fun providesMovieApi(retrofit: Retrofit): ApiService {
        return mockk()
    }
}