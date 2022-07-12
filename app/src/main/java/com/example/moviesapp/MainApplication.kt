package com.example.moviesapp

import android.app.Application
import com.example.moviesapp.database.LocalDatabase
import com.example.moviesapp.models.Movie
import com.example.moviesapp.network.ApiService
import com.example.moviesapp.network.MovieApi
import com.example.moviesapp.repository.MovieRepository
import kotlin.reflect.jvm.internal.impl.descriptors.Visibilities

class MainApplication: Application() {

    lateinit var movieRepository: MovieRepository

    override fun onCreate() {
        super.onCreate()
        initialize()
    }

    private fun initialize() {
        val movieService = MovieApi.retrofitService
        val dataSource = LocalDatabase.getInstance(applicationContext).databaseDao
        movieRepository = MovieRepository(movieService, dataSource)

    }
}