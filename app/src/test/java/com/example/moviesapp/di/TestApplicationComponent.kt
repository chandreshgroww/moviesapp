package com.example.moviesapp.di

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import com.example.moviesapp.MainActivity
import com.example.moviesapp.models.Movie
import com.example.moviesapp.repository.MovieRepositoryTest
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
    modules = [TestNetworkModule::class, TestDatabaseModule::class]
)
interface TestApplicationComponent: ApplicationComponent{

    fun inject(repositoryTest: MovieRepositoryTest)

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance context: Context): TestApplicationComponent
    }
}