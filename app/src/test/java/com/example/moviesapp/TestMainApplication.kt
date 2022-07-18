package com.example.moviesapp

import android.app.Application
import android.util.Log
import com.example.moviesapp.di.DaggerApplicationComponent
import com.example.moviesapp.di.TestApplicationComponent

class TestMainApplication: Application() {

    lateinit var applicationComponent: TestApplicationComponent

    override fun onCreate() {
        super.onCreate()

//        applicationComponent = Dagg
    }
}