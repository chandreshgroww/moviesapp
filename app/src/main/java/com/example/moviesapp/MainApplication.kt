package com.example.moviesapp

import android.app.Application
import com.example.moviesapp.database.LocalDatabase
import com.example.moviesapp.di.ApplicationComponent
import com.example.moviesapp.di.DaggerApplicationComponent
import com.example.moviesapp.models.Movie
import com.example.moviesapp.network.ApiService
import com.example.moviesapp.repository.MovieRepository
import dagger.Component
import kotlin.reflect.jvm.internal.impl.descriptors.Visibilities

class MainApplication: Application() {

    lateinit var applicationComponent: ApplicationComponent

    override fun onCreate() {
        super.onCreate()

        applicationComponent = DaggerApplicationComponent.factory().create(this)
    }
}