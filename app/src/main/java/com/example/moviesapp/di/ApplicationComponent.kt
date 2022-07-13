package com.example.moviesapp.di

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import com.example.moviesapp.MainActivity
import com.example.moviesapp.models.Movie
import com.example.moviesapp.ui.details.DetailsFragment
import com.example.moviesapp.ui.home.HomeFragment
import dagger.Binds
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [NetworkModule::class, DatabaseModule::class, ViewModelModule::class])
interface ApplicationComponent {

    fun injectHome(fragment: HomeFragment)

    fun injectDetails(fragment: DetailsFragment)

    fun getMap(): Map<Class<*>, ViewModel>

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance context: Context, @BindsInstance movie: Movie ?= null): ApplicationComponent
    }
}