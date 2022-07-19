package com.example.moviesapp.di

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import com.example.moviesapp.MainActivity
import com.example.moviesapp.models.Movie
import com.example.moviesapp.ui.details.DetailsFragment
import com.example.moviesapp.ui.explore.BottomListDialogFragment
import com.example.moviesapp.ui.explore.ExploreFragment
import com.example.moviesapp.ui.home.HomeFragment
import dagger.Binds
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [NetworkModule::class, DatabaseModule::class,
        SubComponentModule::class, ViewModelModule::class, InterfaceModule::class]
)
interface ApplicationComponent {

    fun injectHome(fragment: HomeFragment)

    fun injectDetails(fragment: DetailsFragment)

    fun injectBottomSheetFragment(fragment: BottomListDialogFragment)

    fun getMap(): Map<Class<*>, ViewModel>

    fun exploreComponent(): ExploreSubComponent.Factory

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance context: Context): ApplicationComponent
    }
}